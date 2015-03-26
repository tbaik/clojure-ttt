(ns clojure-ttt.board.board-presenter-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.board.board-presenter :refer :all]
            [clojure-ttt.board.board :as board]))

(describe "board-presenter"
  (describe "#repeat-string"
    (it "returns any string repeated by given number of times"
      (should= "1212121212\n"
               (repeat-string 5 "12"))))

  (describe "#blank-line-separator"
    (it "returns a string for separating line"
      (should= "     |     |     |\n"
               (blank-line-separator 3))))

  (describe "#row-separator"
    (it "returns 6 spaces of dashes times width"
      (should= "------------------\n"
               (row-separator 3))))

  (describe "#value-row-string"
    (it "returns row of single digit numbers with correct space in between"
      (should= "  1  |  2  |  3  |  4  |\n"
               (value-row-string '(1 2 3 4)))))

  (describe "#value-block-string"
    (it "returns a string of 3 rows containing values"
      (should= "     |     |     |\n  1  |  2  |  3  |\n     |     |     |\n------------------\n"
               (value-block-string [1 2 3] 3 (board/new-board 3)))))

  (describe "#board-string"
    (it "returns a string with board output string"
      (should= "     |     |     |\n  1  |  2  |  3  |\n     |     |     |\n------------------\n     |     |     |\n  4  |  5  |  6  |\n     |     |     |\n------------------\n     |     |     |\n  7  |  8  |  9  |\n     |     |     |\n------------------\n" 
               (board-string (board/new-board 3))))))
