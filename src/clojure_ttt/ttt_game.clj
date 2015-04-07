(ns clojure-ttt.ttt-game
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.ui :as ui]
            [clojure-ttt.player.players :as players]
            [clojure-ttt.player.player :as player]))

(declare play)

(def undo-stack (atom '()))

(defn exit-system []
  (System/exit 0))

(defn reset-undo-stack []
  (reset! undo-stack '()))

(defn undo-one-move [players board]
  (let [new-board (player/undo-move @undo-stack board)]
    (reset! undo-stack (pop @undo-stack))
    (play players new-board)))

(defn try-undo-turn [players board]
  (if (< (count @undo-stack) 2)
    (do (ui/print-undo-stack-empty-error)
      (play players board))
    (let [new-board (player/undo-turn @undo-stack board)]
      (reset! undo-stack (pop (pop @undo-stack)))
      (play players new-board))))

(defn choose-game-over-options [players board]
  (let [option (ui/prompt-game-over-options)]
    (case option
      "1" nil
      "2" (if (players/is-player-turn players)
            (undo-one-move players board)
            (try-undo-turn (reverse players) board))
      "3" (exit-system)
      (do
        (ui/print-invalid-input-error)
        (choose-game-over-options players board)))))

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
            (ui/print-winner (rules/determine-winner players new-board))
            (choose-game-over-options players new-board))
          (play (reverse players) new-board))))))
