(ns clojure-ttt.players
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.board :as board]
            [clojure-ttt.ui :as ui]))

(declare get-scores)

(defn current-player-piece [players]
  (last (first players)))

(defn current-player-name [players]
  (first (first players)))

(defn receive-human-move [player-name board]
  (let [move (ui/prompt-new-move player-name)]
    (if (rules/is-valid-move? move board)
      move
      (do
        (ui/print-invalid-move-error)
        (receive-human-move player-name board)))))

(defn is-player-turn [players]
  (.startsWith (current-player-name players) "Player"))

(defn game-evaluation [has-winner is-player-turn]
  (cond
    (not has-winner) 1
    (and has-winner is-player-turn) 2
    (and has-winner (not is-player-turn)) 0))

(defn minimax [players board]
  (let [has-winner (rules/has-winner? board)]
    (if (or has-winner (board/full-board? board))
      (game-evaluation has-winner (is-player-turn players))
      (let [scores (get-scores players board)]
        (if (is-player-turn players)
          (apply min scores)
          (apply max scores))))))

(defn get-scores [players board]
  (map #(minimax (reverse players)
                 (board/place-piece
                   (current-player-piece players)
                   (str %)
                   board))
       (board/valid-moves board)))

(defn best-ai-move [players board]
  (let [scores (get-scores players board)
        valid-moves (vec (board/valid-moves board))]
    (get valid-moves (.indexOf scores (apply max scores)))))
