(ns clojure-ttt.ui-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ui :refer :all]))

(describe "ui"
  (around [it]
    (with-out-str (it)))
  (describe "#prompt-new-move"
    (it "prompts with the correct player name"
      (should= "Here is the Game Board, John. Please enter an empty space number or type U to undo the last turn.\n"
               (with-out-str (with-in-str "1"
                               (prompt-new-move "John"))))))

  (describe "#print-invalid-move-error"
    (it "prints that it's invalid"
      (should= "Invalid move! Try again.\n"
               (with-out-str
                 (print-invalid-move-error)))))

  (describe "#print-invalid-input-error"
    (it "prints that the input is invalid"
      (should= "Invalid input! Try again.\n"
               (with-out-str
                 (print-invalid-input-error)))))

  (describe "#print-undo-stack-empty-error"
    (it "prints that the undo stack is empty"
      (should= "There are no moves to undo!\n"
               (with-out-str
                 (print-undo-stack-empty-error)))))

  (describe "#print-placed-piece"
    (it "prints where the piece was placed"
      (should= "Player 1 placed O at 2\n"
               (with-out-str (print-placed-piece "2" ["Player 1" "O"])))))

  (describe "#print-board"
    (it "prints the current board"
      (should= "     |     |     |\n  1  |  2  |  3  |\n     |     |     |\n------------------\n     |     |     |\n  4  |  5  |  6  |\n     |     |     |\n------------------\n     |     |     |\n  7  |  8  |  9  |\n     |     |     |\n------------------\n\n"
              (with-out-str (print-board [1 2 3 4 5 6 7 8 9])))))

  (describe "#print-winner"
    (it "prints for when there is a tie"
      (should= "It's a tie!\n"
               (with-out-str (print-winner "tie"))))
    (it "prints for when there is a winner"
      (should= "X won!\n"
               (with-out-str (print-winner "X")))))

  (describe "#prompt-human-or-ai"
    (it "prints correct text and takes an input"
      (should= "Please type 1 to play against a Computer Player or 2 to play against another Person.\n"
               (with-out-str (with-in-str "1" (prompt-human-or-ai))))))

  (describe "#prompt-turn"
    (it "prints correct text and takes an input"
      (should= "Type 1 to go First(X), 2 to go Second(O), or 3 to exit.\n"
               (with-out-str (with-in-str "2" (prompt-turn)))))))

