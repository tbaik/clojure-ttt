(ns clojure-ttt.ttt-game-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ttt-game :refer :all]
            [clojure-ttt.board :as board]
            [clojure-ttt.ui :as ui]))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(describe "ttt-game"
  (around [it]
    (with-out-str (it)))

  (describe "#current-player-piece"
    (it "returns the piece of the current player"
      (should= "X"
               (current-player-piece [["Player 1" "X"]["Player 2" "O"]]))))

  (describe "#current-player-name"
    (it "returns the name of current player"
      (should= "Player 1"
               (current-player-name [["Player 1" "X"]["Player 2" "O"]]))))

  (describe "#determine-winner"
    (it "returns tie when there is no winner"
      (should= "tie"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["O" "X" "O" "X" "X" "O" "O" "O" "X"])))
    (it "returns the current piece when there is a winner"
      (should= "X"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["X" "X" "X" "O" "O" 6 7 8 9]))))

  (describe "#receive-human-move"
    (it "returns a string for move to place if valid input on board"
      (should= "1"
               (with-in-str "1"
                 (receive-human-move "Player 1" (board/new-board 3))))))

  (describe "#play"
    (it "presents board each time play begins and at the end, game ends when there's a winner"
      (let [times-printed-board (atom 0) times-printed-piece (atom 0)]
        (with-redefs [ui/print-board (fn [_] (swap! times-printed-board inc))
                      ui/print-placed-piece (fn [_ _] (swap! times-printed-piece inc))]
          (should= 0 @times-printed-board)
          (should= 0 @times-printed-piece)
          (with-in-str (make-input '(1 4 2 5 3))
            (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))
            (should= 6 @times-printed-board)
            (should= 5 @times-printed-piece)))))
    (it "returns the correct winner in the end"
      (with-in-str (make-input '(1 4 2 5 3))
        (should= "X" (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))))
      (with-in-str (make-input '(1 5 3 2 8 4 6 9 7))
        (should= "tie" (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))))))
