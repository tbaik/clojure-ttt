(ns clojure-ttt.board-presenter
  (:require [clojure-ttt.board :as board]))

(defn repeat-string [times string]
  (apply str (concat (repeat times string) "\n")))

(defn blank-line-separator [width]
  (repeat-string width "     |"))

(defn row-separator [width]
  (repeat-string width "------"))

(defn value-row-string [value-list]
  (str (reduce str (for [value value-list] (str "  " value "  |"))) "\n"))

(defn create-row-value-list [start end board]
  (subvec board start end))

(defn value-block-string [index width board]
  (str (blank-line-separator width)
       (value-row-string (create-row-value-list index (+ index width) board))
       (blank-line-separator width)
       (row-separator width)))

(defn board-string
  [board]
  (let [width (board/board-width board)]
    (loop [index 0 string ""]
      (cond
        (< index (count board))
          (recur (+ width index) (str string (value-block-string index width board)))
        :else string))))
