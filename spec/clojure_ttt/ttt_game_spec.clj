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

  (describe "#determine-winner"
    (it "returns tie when there is no winner"
      (should= "tie"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["O" "X" "O" "X" "X" "O" "O" "O" "X"])))
    (it "returns the current piece when there is a winner"
      (should= "X"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["X" "X" "X" "O" "O" 6 7 8 9]))))

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
