(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :as core]
            [clojure-ttt.ttt-game :as game]
            [clojure.java.io :as io]))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(describe "integration tests"
  (describe "#gameloop"
    (it "plays a game from start to finish."
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 4 2 5 3 3))
                    (core/gameloop))))
          (should-contain "Player 1 placed X at 1" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 4" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 2" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 5" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 3" (slurp "integration-test.txt"))
          (should-contain "X won!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
     (it "computer player automatically makes a move when it's computer turn without human input"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 1 1 2 4 3))
                    (core/gameloop))))
          (should-contain "Player 1 placed X at 1" (slurp "integration-test.txt"))
          (should-contain "Computer placed O at 5" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 2" (slurp "integration-test.txt"))
          (should-contain "Computer placed O at 3" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 4" (slurp "integration-test.txt"))
          (should-contain "Computer placed O at 7" (slurp "integration-test.txt"))
          (should-contain "O won!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
     (it "makes nine moves possible"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 2 3 4 5 6 8 7 9 3))
                    (core/gameloop))))
          (should-contain "Player 1 placed X at 1" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 2" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 3" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 4" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 5" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 6" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 8" (slurp "integration-test.txt"))
          (should-contain "Player 2 placed O at 7" (slurp "integration-test.txt"))
          (should-contain "Player 1 placed X at 9" (slurp "integration-test.txt"))
          (should-contain "X won!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "is not able to make invalid moves (Repeat same move)"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 1 4 2 5 3 3))
                    (core/gameloop))))
          (should-contain "Player 1 placed X at 1" (slurp "integration-test.txt"))
          (should-contain "Invalid move! Try again." (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "is not able to make invalid moves (Numeric input for move input)"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 "invalidinput" 1 4 2 5 3 3))
                    (core/gameloop))))
          (should-contain "Invalid move! Try again." (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "should be able to undo previously made move"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 1 1 "U" 1 2 4 3 3))
                    (core/gameloop))))
          (should-not-contain "Invalid move! Try again." (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "should be able see victory message for my win as X"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 4 2 5 3 3))
                    (core/gameloop))))
          (should-contain "X won!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "should be able see loss message for my loss as X"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 4 2 5 7 6 3))
                    (core/gameloop))))
          (should-contain "O won!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "should be able see tie message when game is a tie"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 2 1 5 3 2 8 4 6 9 7 3))
                    (core/gameloop))))
          (should-contain "It's a tie!" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "continue playing more games even after gameover until we tell it to exit"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 1 1 2 4 1 1 1 1 2 4 3))
                    (core/gameloop))))
          (should-contain "Type 1 to start a new game, 2 to Undo last move, or 3 to exit." (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))
    (it "prints the correct gameboard upon change from both human input and computer move"
      (let [times-exit-called (atom 0)]
        (with-redefs [game/exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (spit "integration-test.txt"
                (with-out-str
                  (with-in-str
                    (make-input '(1 1 1 2 4 3))
                    (core/gameloop))))
          (should-contain "     |     |     |\n  1  |  2  |  3  |\n     |     |     |\n------------------\n     |     |     |\n  4  |  5  |  6  |\n     |     |     |\n------------------\n     |     |     |\n  7  |  8  |  9  |\n     |     |     |\n------------------\n" (slurp "integration-test.txt"))
          (should-contain "     |     |     |\n  X  |  2  |  3  |\n     |     |     |\n------------------\n     |     |     |\n  4  |  5  |  6  |\n     |     |     |\n------------------\n     |     |     |\n  7  |  8  |  9  |\n     |     |     |\n------------------\n" (slurp "integration-test.txt"))

          (should-contain "     |     |     |\n  X  |  2  |  3  |\n     |     |     |\n------------------\n     |     |     |\n  4  |  O  |  6  |\n     |     |     |\n------------------\n     |     |     |\n  7  |  8  |  9  |\n     |     |     |\n------------------\n" (slurp "integration-test.txt"))
          (io/delete-file "integration-test.txt"))))))
