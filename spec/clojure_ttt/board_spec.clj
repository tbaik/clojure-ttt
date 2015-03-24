(ns clojure-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.board :refer :all]))

(describe "board"
  (describe "#new-board"
    (it "returns a vector with 1-9 given 3 as board width"
      (should= [1 2 3 4 5 6 7 8 9]
               (new-board 3)))
    (it "returns a vector with 1-16 given 4 as board width"
      (should= [1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16]
               (new-board 4))))

  (describe "#board-width"
    (it "returns the width given the board"
      (should= 3
               (board-width (new-board 3)))))

  (describe "parse-int"
    (it "returns any int it finds in a string"
      (should= 33
               (parse-int "33"))))

  (describe "place-piece"
    (it "returns a board with piece placed in that spot"
      (should= [1 2 3 4 "O" 6 7 8 9]
               (place-piece "O" "5" (new-board 3)))))

  (describe "valid-moves"
    (it "returns a coll of available moves from the given board"
      (should= [1 4 5]
               (valid-moves [1 "X" "O" 4 5 "X" "O" "X" "O"]))))

  (describe "#full-board?"
    (it "returns true if board spaces are all taken up"
      (should= true
               (full-board? ["O" "X" "O" "X" "O" "X" "O" "X" "O"])))
    (it "returns false if board spaces are not filled up"
      (should= false
               (full-board? ["O" "X" "O" "X" "O" "X" "O" "X" 9])))))
