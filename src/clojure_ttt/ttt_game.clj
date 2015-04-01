(ns clojure-ttt.ttt-game
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.ui :as ui]
            [clojure-ttt.player.players :as players]
            [clojure-ttt.player.player :as player]))

(declare play)

(def undo-stack (atom '()))

(defn reset-undo-stack []
  (reset! undo-stack '()))

(defn determine-winner [players board]
  (if (rules/has-winner? board)
    (players/current-player-piece players)
    "tie"))

(defn try-undo-turn [players board]
  (if (< (count @undo-stack) 2)
    (do (ui/print-undo-stack-empty-error)
      (play players board))
    (let [new-board (board/undo-turn @undo-stack board)]
      (reset! undo-stack (pop (pop @undo-stack)))
      (play players new-board))))

(defn play [players board]
  (ui/print-board board)
  (let [move (player/choose-move players board)]
    (if (= "U" move)
      (try-undo-turn players board)
      (let [new-board (board/place-piece (players/current-player-piece players) move board)]
        (ui/print-placed-piece move (first players))
        (swap! undo-stack conj move)
        (if (rules/game-over? new-board)
          (do
            (ui/print-board new-board)
            (determine-winner players new-board))
          (play (reverse players) new-board))))))
