(ns clojure-ttt.ttt-game
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board :as board]
            [clojure-ttt.ui :as ui]))

(defn current-player-piece [players]
  (last (first players)))

(defn current-player-name [players]
  (first (first players)))

(defn determine-winner [players board]
  (if (rules/has-winner? board)
    (current-player-piece players)
    "tie"))

(defn receive-human-move [player-name board]
  (let [move (ui/prompt-new-move player-name)]
    (if (rules/is-valid-move? move board)
      move
      (do
        (ui/print-invalid-move-error)
        (receive-human-move player-name board)))))

(defn play [players board]
  (ui/print-board  board)
  (let [move (receive-human-move (current-player-name players) board)]
    (let [new-board (board/place-piece (current-player-piece players) move board)]
      (ui/print-placed-piece move (first players))
      (if (rules/game-over? new-board)
        (do
          (ui/print-board new-board)
          (determine-winner players new-board))
        (play (reverse players) new-board)))))
