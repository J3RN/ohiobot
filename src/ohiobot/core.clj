(ns ohiobot.core
  (:require
    [irclj.core :as irclj]
    [clojure.string :as string]
    [clojure.data.json :as json]
    [korma.core :refer :all]
    [korma.db :refer :all]))

(def settings
  (json/read-str
    (slurp "settings.json")))

(def db-name  (get settings "db-name"))
(def db-user  (get settings "db-user"))
(def db-pass  (get settings "db-pass"))

(def server   (get settings "irc-server"))
(def port     (get settings "irc-port"))
(def nick     (get settings "irc-nick"))
(def channels (get settings "channels"))
(def master   (get settings "master"))

(defdb db (postgres {:db db-name
                     :user db-user
                     :password db-pass}))
(defentity users)

(defn io [irc args nick]
  (irclj/reply irc args (str nick ": IO!"))
  (let [botted (:botted
                 (first
                   (select users
                           (fields [:botted])
                           (where {:nick nick}))))]
    (if (= botted nil)
      (insert users
              (values {:nick nick :botted 1}))
      (update users
              (set-fields {:botted (+ 1 botted)})
              (where {:nick nick})))))

(defn io-count [irc args message]
  (if (> (count (string/split message #" ")) 1)
        (let [user (second (string/split message #" "))]
          (let [botted (:botted
                         (first
                           (select users
                                   (fields [:botted])
                                   (where {:nick user}))))]
            (if (not (nil? botted))
              (irclj/reply irc args (str user " has been IO'd " botted " times"))
              (irclj/reply irc args (str user " has never been IO'd")))))
        (let [counts (select users
                             (order :botted :DESC)
                             (limit 5))]
          (irclj/reply irc args (reduce
                                  #(str %1 (:nick %2) ": " (:botted %2) " ")
                                  ""
                                  counts)))))

(defn is-oh [message]
  (not (nil? (re-matches #"o\W*h\W*" (string/lower-case message)))))

(defn callback [irc args]
  (let [message (:text args)
        tokens (string/split (:text args) #" ")
        nick (:nick args)]
    (cond
      (is-oh message)
      (io irc args nick)

      (= (first tokens) "#ios")
      (io-count irc args message)

      (= (first tokens) "#join")
      (if (= nick master)
        (let [channel (get tokens 1)]
          (irclj/join irc (get tokens 1))
          (irclj/reply irc args (str "Joined " channel))))

      (= (first tokens) "#part")
      (if (= nick master)
        (let [channel (get tokens 1)]
          (irclj/reply irc args "Bye!")
          (irclj/part irc channel))))))

(defn start []
  (let [connection (irclj/connect server port nick :callbacks {:privmsg callback})]
    (doseq [channel channels]
      (irclj/join connection channel)
      (println "Joined " channel))))
