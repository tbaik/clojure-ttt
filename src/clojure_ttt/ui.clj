(ns clojure-ttt.ui
  (:require [clojure-ttt.board.board-presenter :as board-presenter]))

(defn prompt [message]
  (println message)
  (read-line))

(defn print-message [message]
  (println message))

(defn prompt-new-move [player-name]
  (prompt (str "Here is the Game Board, " player-name ". Please enter an empty space number.")))

(defn print-invalid-move-error []
  (print-message "Invalid move! Try again."))

(defn print-placed-piece [move player]
  (print-message (str (first player) " placed " (last player) " at " move)))

(defn print-board [board]
  (print-message (board-presenter/board-string board)))

(defn print-winner [winner]
  (if (= winner "tie")
    (print-message "It's a tie!")
    (print-message (str winner " won!"))))
