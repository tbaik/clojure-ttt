(ns clojure-ttt.board)

(declare vector_to_string)

(defn new_board
  [width]
  (vec (range 1 (inc (* width width)))))

(defn place_piece
  [piece spot board]
  (replace {spot piece} board))

(defn repeat_string
  [times string]
  (apply str (concat (repeat times string) "\n")))

(defn blank_line_separator
  [width]
  (repeat_string width "     |"))

(defn row_separator
  [width]
  (repeat_string width "------"))

(defn value_row_string
  [value_list]
  (reduce str (for [value value_list] (str "  " value "  |"))))

(defn create_row_value_list
  [start end board]
  (subvec board start end))


;(defn board_string
;[board]
;(concat (for [x board] x)))
