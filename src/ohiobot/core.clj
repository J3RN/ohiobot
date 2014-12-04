(ns ohiobot.core
  (:require
    [irclj.core :refer :all]
    [clojure.string :as string]
    [korma :refer :all]))

(def db-host "localhost")
(def db-port 5432)
(def db-name "ohiobot")
(def db-user "jonathan")
(def db-pass "")

(def db (postgres {:db db-name
                   :user db-user
                   :password db-pass}))
(defentity users)

(defn callback [irc args]
  (let [message (:text args)]
    (if
      (= (apply str (re-seq #"[A-Za-z]" (string/lower-case message))) "oh")
      (do
        (reply irc args "IO!")
        (j/insert! db :users
                   {:nick (:nick args) })))))

(defn start []
  (let [connection (connect "irc.freenode.net" 6667 "Ohiobot" :callbacks {:privmsg callback})]
    (join connection "#osuosc-hangman")))
