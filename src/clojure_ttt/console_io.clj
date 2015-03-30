(ns clojure-ttt.console-io)

(defn prompt [message]
  (println message)
  (read-line))

(defn print-message [message]
  (println message))
