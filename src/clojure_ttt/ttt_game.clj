(ns clojure-ttt.ttt-game
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board :as board]
            [clojure-ttt.board-presenter :as board-presenter]))

(defn prompt [message]
  (println message)
  (read-line))

(defn print-message [message]
  (println message))

(defn current-player-piece [players]
  (last (first players)))

(defn current-player-name [players]
  (first (first players)))

(defn receive-human-move [player-name board]
  (let [move (prompt (str "Here is the Game Board, " player-name ". Please enter an empty space number."))]
    (if (rules/is-valid-move? move board)
      move
      (do
        (print-message "Invalid move! Try again.")
        (receive-human-move player-name board)))))

(defn print-placed-piece [move player]
  (print-message (str (first player) " placed " (last player) " at " move)))

(defn play [players board]
  (print-message (board-presenter/board-string board))
  (let [move (receive-human-move (current-player-name players) board)]
    (let [new-board (board/place-piece (current-player-piece players) move board)]
      (print-placed-piece move (first players))
      (if (rules/has-winner? new-board)
        (do
          (print-message (board-presenter/board-string new-board))
          (print-message "Game over! Will implement winner printout soon"))
        (play (reverse players) new-board)))))
