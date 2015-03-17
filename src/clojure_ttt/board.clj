(ns clojure-ttt.board)

(defn new-board [width]
  (vec (range 1 (inc (* width width)))))

(defn place-piece [piece spot board]
  (replace {spot piece} board))

(defn board-width [board]
  (int (Math/sqrt (count board))))
