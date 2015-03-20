(ns clojure-ttt.ttt-game-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ttt-game :refer :all]
            [clojure-ttt.board :as board]))

(describe "ttt-game"
  (around [it]
    (with-out-str (it)))
  (describe "#prompt"
    (it "tests the input"
      (should= "1"
               (with-in-str "1"
                 (prompt "enter some amount"))))
    (it "tests the output"
      (should= "enter some amount\n"
               (with-out-str (with-in-str "1"
                               (prompt "enter some amount"))))))
  (describe "#print-message"
    (it "tests the output"
      (should= "hello world\n"
               (with-out-str (print-message "hello world")))))
  (describe "#current-player-piece"
    (it "returns the piece of the current player"
      (should= "X"
               (current-player-piece [["Player 1" "X"]["Player 2" "O"]]))))
  (describe "#current-player-name"
    (it "returns the name of current player"
      (should= "Player 1"
               (current-player-name [["Player 1" "X"]["Player 2" "O"]]))))
  (describe "#receive-human-move"
    (it "returns a string for move to place if valid input on board"
      (should= "1"
               (with-in-str "1"
                 (receive-human-move "Player 1" (board/new-board 3))))))
  (describe "#print-placed-piece"
    (it "prints where the piece was placed"
      (should= "Player 1 placed O at 2\n"
               (with-out-str (print-placed-piece "2" ["Player 1" "O"])))))
  (describe "#play"
    (it "returns true if game is over"
      (should-invoke play {:with [[["Player 1" "O"] ["Player 2" "O"]] (board/new-board 3)]
                           :return ["1" "5" "2" "6" "3"]}
               (play [["Player 1" "O"] ["Player 2" "O"]] (board/new-board 3))))))


