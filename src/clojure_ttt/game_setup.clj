(ns clojure-ttt.game-setup
  (:require [clojure-ttt.ui :as ui]))

(defn build-opponent []
  (let [opponent-input (ui/prompt-human-or-ai)]
    (cond
      (= "1" opponent-input) "Computer"
      (= "2" opponent-input) "Player 2"
      :else (do
              (ui/print-invalid-input-error)
              (build-opponent)))))

(defn exit-system []
  (System/exit 0))

(defn build-turn []
  (let [turn-input (ui/prompt-turn)]
    (cond
      (= "1" turn-input) "X"
      (= "2" turn-input) "O"
      (= "3" turn-input) (exit-system)
      :else (do
              (ui/print-invalid-input-error)
              (build-turn)))))

(defn create-players []
  (let [player-turn (build-turn) opponent-type (build-opponent)]
    (if (= player-turn "X")
      (vector (vector "Player 1" player-turn) (vector opponent-type "O"))
      (vector (vector opponent-type "X") (vector "Player 1" player-turn)))))

