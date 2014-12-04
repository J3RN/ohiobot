(ns ohiobot.core
  (:require
    [irclj.core :as irclj]
    [clojure.string :as string]
    [korma.core :refer :all]
    [korma.db :refer :all]))

(def db-host "localhost")
(def db-port 5432)
(def db-name "ohiobot")
(def db-user "jonathan")
(def db-pass "")

(defdb db (postgres {:db db-name
                   :user db-user
                   :password db-pass}))
(defentity user)

(defn callback [irc args]
  (let [message (:text args)]
    (if
      (= (apply str (re-seq #"[A-Za-z]" (string/lower-case message))) "oh")
      (do
        (irclj/reply irc args "IO!")))))

(defn start []
  (let [connection (irclj/connect "irc.freenode.net" 6667 "Ohiobot" :callbacks {:privmsg callback})]
    (irclj/join connection "#osuosc-hangman")))
