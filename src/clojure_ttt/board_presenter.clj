(ns clojure-ttt.board-presenter
  (:require [clojure-ttt.board :as board]))

(defn repeat-string [times string]
  (apply str (concat (repeat times string) "\n")))

(defn blank-line-separator [width]
  (repeat-string width "     |"))

(defn row-separator [width]
  (repeat-string width "------"))

(defn value-row-string [value-coll]
  (str (reduce str (for [value value-coll] (str "  " value "  |"))) "\n"))

(defn value-block-string [row-coll width board]
  (str (blank-line-separator width)
       (value-row-string row-coll)
       (blank-line-separator width)
       (row-separator width)))

(defn board-string [board]
  (let [width (board/board-width board)]
    (apply str (map #(value-block-string % width board) (partition width board)))))
