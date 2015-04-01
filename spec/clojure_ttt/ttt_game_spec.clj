(ns clojure-ttt.ttt-game-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ttt-game :refer :all]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.ui :as ui]))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(describe "ttt-game"
  (around [it]
    (with-out-str (it)))

  (describe "#reset-undo-stack"
    (it "resets the undo-stack to empty list"
      (reset-undo-stack)
      (should= '() @undo-stack)))

  (describe "#determine-winner"
    (it "returns tie when there is no winner"
      (should= "tie"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["O" "X" "O" "X" "X" "O" "O" "O" "X"])))
    (it "returns the current piece when there is a winner"
      (should= "X"
               (determine-winner [["Player 1" "X"]["Player 2" "O"]]
                                 ["X" "X" "X" "O" "O" 6 7 8 9]))))

  (describe "#try-undo-turn"
    (it "returns error if undo is called on undo stack less than size of 2"
      (let [times-printed-error (atom 0)
            times-play-called (atom 0)]
        (with-redefs [ui/print-undo-stack-empty-error (fn [] (swap! times-printed-error inc))
                      play (fn [_ _] (swap! times-play-called inc))]
          (reset-undo-stack)
          (should= 0 @times-printed-error)
          (should= 0 @times-play-called)
          (try-undo-turn [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))
          (should= 1 @times-printed-error)
          (should= 1 @times-play-called))))
    (it "calls undo turn function if undo stack is not empty and pops two from stack after"
      (let [times-undo-called (atom 0)
            times-play-called (atom 0)]
        (with-redefs [board/undo-turn (fn [_ _] (swap! times-undo-called inc))
                      play (fn [_ _] (swap! times-play-called inc))]
          (reset! undo-stack '("8" "9"))
          (should= 0 @times-undo-called)
          (should= 0 @times-play-called)
          (try-undo-turn [["Player 1" "X"] ["Player 2" "O"]] [1 2 3 4 5 6 7 "O" "X"])
          (should= 1 @times-undo-called)
          (should= 1 @times-play-called)))))

  (describe "#play"
    (it "presents board each time play begins and at the end, game ends when there's a winner"
      (let [times-printed-board (atom 0) times-printed-piece (atom 0)]
        (with-redefs [ui/print-board (fn [_] (swap! times-printed-board inc))
                      ui/print-placed-piece (fn [_ _] (swap! times-printed-piece inc))]
          (reset-undo-stack)
          (should= 0 @times-printed-board)
          (should= 0 @times-printed-piece)
          (should= '() @undo-stack)
          (with-in-str (make-input '(1 4 2 5 3))
            (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))
            (should= 6 @times-printed-board)
            (should= 5 @times-printed-piece)
            (should= '("3" "5" "2" "4" "1") @undo-stack)))))
    (it "returns the correct winner in the end"
      (with-in-str (make-input '(1 4 2 5 3))
        (should= "X" (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))))
      (with-in-str (make-input '(1 5 3 2 8 4 6 9 7))
        (should= "tie" (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))))))
