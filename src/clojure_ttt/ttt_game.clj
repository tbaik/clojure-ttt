(ns clojure-ttt.ttt-game
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board :as board]
            [clojure-ttt.ui :as ui]
            [clojure-ttt.player.players :as players]
            [clojure-ttt.player.human-player :as human]))

(defn determine-winner [players board]
  (if (rules/has-winner? board)
    (players/current-player-piece players)
    "tie"))

(defn play [players board]
  (ui/print-board board)
  (let [move (human/receive-human-move (players/current-player-name players) board)]
    (let [new-board (board/place-piece (players/current-player-piece players) move board)]
      (ui/print-placed-piece move (first players))
      (if (rules/game-over? new-board)
        (do
          (ui/print-board new-board)
          (determine-winner players new-board))
        (play (reverse players) new-board)))))
