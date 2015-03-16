(ns clojure-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.board :refer :all]))

(describe "clojure-ttt.board"
          (describe "new_board"
                    (it "returns a vector with 1-9 given 3 as board width"
                        (should= [1 2 3 4 5 6 7 8 9]
                                 (new_board 3)))
                    (it "returns a vector with 1-16 given 4 as board width"
                        (should= [1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16]
                                 (new_board 4))))
          (describe "place_piece"
                    (it "returns a board with piece placed in that spot"
                        (should= [1 2 3 4 "O" 6 7 8 9]
                                 (place_piece "O" 5 (new_board 3)))))
          (describe "repeat_string"
                    (it "returns any string repeated by given number of times"
                        (should= "1212121212\n"
                                 (repeat_string 5 "12"))))
          (describe "blank_line_separator"
                    (it "returns a string for separating line"
                        (should= "     |     |     |\n"
                                 (blank_line_separator 3))))
          (describe "row_separator"
                    (it "returns 6 spaces of dashes times width"
                        (should= "------------------\n"
                                 (row_separator 3))))
          (describe "value_row_string"
                    (it "returns row of single digit numbers with correct space in between"
                        (should= "  1  |  2  |  3  |  4  |"
                                 (value_row_string '(1 2 3 4)))))
          (describe "create_row_value_list"
                    (it "returns a list of values from the start and end index of board"
                        (should= [4 5 6]
                                 (create_row_value_list 3 6 (new_board 3))))))
;(describe "board_string"
;(it "returns a string with board output string"
;(should= "123456789"
;(board_string (new_board 3))))))

