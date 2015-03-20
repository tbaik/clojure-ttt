(ns clojure-ttt.ttt-rules-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ttt-rules :refer :all]
            [clojure-ttt.board :as board]))
(describe "ttt-rules"

  (describe "#filter-by-index"
    (it "returns the coll of values at the indexes given"
      (should= [1 5 9]
               (filter-by-index [0 2 4] [1 2 5 8 9]))))
  (describe "#contains-same-pieces"
    (it "returns true if all spaces of are the same"
      (should= true
               (contains-same-pieces ["O" "O" "O"])))
    (it "returns false if all spaces are not the same"
      (should= false
               (contains-same-pieces ["O" "X" "O"]))))
  (describe "#get-diagonal-indexes"
    (it "returns the correct indexes for different size boards"
      (should= [[0 3] [1 2]]
               (get-diagonal-indexes 2))
      (should= [[0 4 8] [2 4 6]]
               (get-diagonal-indexes 3))))
  (describe "#separate-into-rows"
    (it "returns rows as coll of 2 colls for a width 2 board"
      (should= [[1 2] [3 4]]
               (separate-into-rows 2 (board/new-board 2)))))
  (describe "#separate-into-columns"
    (it "returns columns as coll of 3 colls for a width 3 board"
      (should= [[1 4 7] [2 5 8] [3 6 9]]
               (separate-into-columns 3 (board/new-board 3))))
    (it "works for strings as well"
      (should= [["O" "O" "O"] [2 5 8] ["X" "O" 9]]
               (separate-into-columns 3 ["O" 2 "X" "O" 5 "O" "O" 8 9]))))
  (describe "#separate-into-diagonals"
    (it "returns diagonals as coll of 2 colls for both diagonals"
      (should= [[1 5 9] [3 5 7]]
               (separate-into-diagonals 3 (board/new-board 3)))))
  (describe "#has-winner-from-separation?"
    (it "returns true if any of the given coll contains same pieces"
      (should= true
               (has-winner-from-separation? [["O" "O" "O"] [2 5 8] ["X" "O" 9]]))))
  (describe "#has-horizontal-winner?"
    (it "returns true if has winner on any row"
      (should= true
               (has-horizontal-winner? 3 ["O" "O" "O" 4 5 6 7 8 9]))
      (should= true
               (has-horizontal-winner? 3 ["O" "X" "O" "X" "X" "X" "O" "X" "X"])))
    (it "returns nil if all rows dont have a winner"
      (should= nil
               (has-horizontal-winner? 3 ["O" "X" "O" 4 5 6 7 8 9]))
      (should= nil
               (has-horizontal-winner? 3 ["O" "X" "O" "X" "X" "O" "O" "X" "X"]))))
  (describe "#has-vertical-winner?"
    (it "returns true if has winner on any column"
      (should= true
               (has-vertical-winner? 3 ["O" "O" "O" "O" "X" "X" "O" "X" "O"])))
    (it "returns nil if all columns dont have a winner"
      (should= nil
               (has-vertical-winner? 3 ["O" "O" "O" "X" "X" "X" "O" "O" "O"]))))
  (describe "#has-diagonal-winner?"
    (it "returns true if has winner on a left diagonal"
      (should= true
               (has-diagonal-winner? 3 ["O" "X" "X" "X" "O" "X" "O" "X" "O"])))
    (it "returns true if has winner on right diagonal"
      (should= true
               (has-diagonal-winner? 3 ["X" "X" "O" "X" "O" "X" "O" "X" "O"])))
    (it "returns nil if does not have winner"
      (should= nil
               (has-diagonal-winner? 3 (board/new-board 3)))))
  (describe "#has-winner?"
    (it "returns true if there is a winner in any combination"
      (should= true
               (has-winner? ["O" "X" "O" "X" "O" "X" "O" "X" "O"])))
    (it "returns nil if there is no winner"
      (should= nil
               (has-winner? (board/new-board 3)))))
  (describe "#is-valid-move?"
    (it "returns true if spot is not taken in the board"
      (should= true
               (is-valid-move? "1" (board/new-board 3))))
    (it "returns nil if spot is taken in the board"
      (should= nil
               (is-valid-move? "1" (board/place-piece "O" "1" (board/new-board 3)))))))

