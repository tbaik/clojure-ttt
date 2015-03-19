(ns clojure-ttt.ttt-rules
  (:require [clojure-ttt.board :as board]))

(defn contains-same-pieces [coll]
  (apply = coll))

(defn separate-into-rows [width board]
  (partition width board))

(defn separate-into-columns [width board]
  (apply map vector (separate-into-rows width board)))

(defn has-winner-from-separation [separated-coll]
  (some true? (map contains-same-pieces separated-coll)))

(defn has-horizontal-winner [board]
  (has-winner-from-separation (separate-into-rows (board/board-width board) board)))

(defn has-vertical-winner [board]
  (has-winner-from-separation (separate-into-columns (board/board-width board) board)))

(defn has-diagonal-winner [board]
  ())

