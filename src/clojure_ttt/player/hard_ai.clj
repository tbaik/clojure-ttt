(ns clojure-ttt.player.hard-ai
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.player.players :as players]
            [clojure-ttt.board :as board]))

(declare get-scores)

(defn game-evaluation [has-winner is-player-turn]
  (cond
    (not has-winner) 1
    (and has-winner is-player-turn) 2
    (and has-winner (not is-player-turn)) 0))

(defn minimax [players board]
  (let [has-winner (rules/has-winner? board)]
    (if (or has-winner (board/full-board? board))
      (game-evaluation has-winner (players/is-player-turn players))
      (let [scores (get-scores players board)]
        (if (players/is-player-turn players)
          (apply min scores)
          (apply max scores))))))

(defn get-scores [players board]
  (map #(minimax (reverse players)
                 (board/place-piece
                   (players/current-player-piece players)
                   (str %)
                   board))
       (board/valid-moves board)))

(defn best-ai-move [players board]
  (let [scores (get-scores players board)
        valid-moves (vec (board/valid-moves board))]
    (str (get valid-moves (.indexOf scores (apply max scores))))))
