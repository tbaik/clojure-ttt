(ns clojure-ttt.board)

(defn new-board [width]
  (vec (range 1 (inc (* width width)))))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn place-piece [piece spot board]
  (replace {(parse-int spot) piece} board))

(defn board-width [board]
  (int (Math/sqrt (count board))))

(defn valid-moves [board]
  (filter integer? board))

(defn full-board? [board]
  (= 0 (count (valid-moves board))))
