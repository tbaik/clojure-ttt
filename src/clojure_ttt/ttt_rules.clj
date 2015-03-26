(ns clojure-ttt.ttt-rules
  (:require [clojure-ttt.board.board :as board]))

(defn filter-by-index [indexes coll]
    (keep-indexed #(when ((set indexes) %1) %2) coll))

(defn contains-same-pieces [coll]
  (apply = coll))

(defn get-diagonal-indexes [width]
  (apply map #(take width (iterate (partial + %1) %2))
         [[(inc width) (dec width)]
          [0 (dec width)]]))

(defn separate-into-rows [width board]
  (partition width board))

(defn separate-into-columns [width board]
  (apply map vector (separate-into-rows width board)))

(defn separate-into-diagonals [width board]
  (map #(filter-by-index % board) (get-diagonal-indexes width)))

(defn has-winner-from-separation? [separated-coll]
  (some true? (map contains-same-pieces separated-coll)))

(defn has-horizontal-winner? [width board]
  (has-winner-from-separation? (separate-into-rows width board)))

(defn has-vertical-winner? [width board]
  (has-winner-from-separation? (separate-into-columns width board)))

(defn has-diagonal-winner? [width board]
  (has-winner-from-separation? (separate-into-diagonals width board)))

(defn has-winner? [board]
  (let [width (board/board-width board)]
    (and (< (count (board/valid-moves board)) 5)
         (or (has-horizontal-winner? width board)
             (has-vertical-winner? width board)
             (has-diagonal-winner? width board)))))

(defn is-valid-move? [move board]
  (some #(= move (str %)) board))

(defn game-over? [board]
  (or (has-winner? board)
      (board/full-board? board)))

