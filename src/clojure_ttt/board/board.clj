(ns clojure-ttt.board.board)

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

(defn undo-move [move board]
  (assoc board (dec (parse-int move)) (parse-int move)))

(defn undo-turn [undo-stack board]
  (undo-move (peek (pop undo-stack))
             (undo-move (peek undo-stack) board)))
