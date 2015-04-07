(ns clojure-ttt.ttt-game-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.ttt-game :refer :all]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.player.player :as player]
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

  (describe "#undo-one-move"
    (it "calls undo move and pops one from the undo-stack and then calls play"
      (let [times-undo-called (atom 0)
            times-play-called (atom 0)]
        (with-redefs [player/undo-move (fn [_ _] (swap! times-undo-called inc))
                      play (fn [_ _] (swap! times-play-called inc))]
          (reset! undo-stack '("8" "9"))
          (should= 0 @times-undo-called)
          (should= 0 @times-play-called)
          (undo-one-move [["Player 1" "X"] ["Player 2" "O"]] [1 2 3 4 5 6 7 "O" "X"])
          (should= 1 @times-undo-called)
          (should= 1 @times-play-called)))))

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
        (with-redefs [player/undo-turn (fn [_ _] (swap! times-undo-called inc))
                      play (fn [_ _] (swap! times-play-called inc))]
          (reset! undo-stack '("8" "9"))
          (should= 0 @times-undo-called)
          (should= 0 @times-play-called)
          (try-undo-turn [["Player 1" "X"] ["Player 2" "O"]] [1 2 3 4 5 6 7 "O" "X"])
          (should= 1 @times-undo-called)
          (should= 1 @times-play-called)))))

  (describe "#choose-game-over-options"
    (it "does nothing if starting a new game"
      (should= nil
               (with-in-str "1" (choose-game-over-options
                                  [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))))
    (it "undo move twice if computer turn with undo option"
      (let [times-try-undo-turn-called (atom 0)]
        (with-redefs [try-undo-turn (fn [_ _] (swap! times-try-undo-turn-called inc))]
          (should= 0 @times-try-undo-turn-called)
          (with-in-str "2" (choose-game-over-options
                             [["Computer 1" "X"] ["Player 2" "O"]] (board/new-board 3)))
          (should= 1 @times-try-undo-turn-called))))
    (it "undo move once if human turn with undo option"
      (let [times-undo-turn-called (atom 0)]
        (with-redefs [undo-one-move (fn [_ _] (swap! times-undo-turn-called inc))]
          (should= 0 @times-undo-turn-called)
          (with-in-str "2" (choose-game-over-options
                             [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))
          (should= 1 @times-undo-turn-called))))
    (it "exits with exit option"
      (let [times-exit-called (atom 0)]
        (with-redefs [exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (with-in-str "3"
            (choose-game-over-options
              [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))
          (should= 1 @times-exit-called))))
    (it "prints invalid input error and calls this function again with invalid option"
      (let [times-invalid-error-called (atom 0)]
        (with-redefs [ui/print-invalid-input-error (fn [] (swap! times-invalid-error-called inc))
                      ]
          (should= 0 @times-invalid-error-called)
          (with-in-str (make-input '("invalidstuff" "1"))
            (choose-game-over-options
              [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3)))
          (should= 1 @times-invalid-error-called)))))

  (describe "#play"
    (it "presents board each time play begins and at the end, game ends when there's a winner"
      (let [times-printed-board (atom 0) times-printed-piece (atom 0)
            times-exit-called (atom 0)]
        (with-redefs [ui/print-board (fn [_] (swap! times-printed-board inc))
                      ui/print-placed-piece (fn [_ _] (swap! times-printed-piece inc))
                      exit-system (fn [] (swap! times-exit-called inc))]
          (reset-undo-stack)
          (should= 0 @times-printed-board)
          (should= 0 @times-printed-piece)
          (should= 0 @times-exit-called)
          (should= '() @undo-stack)
          (with-in-str (make-input '(1 4 2 5 3 3))
            (play [["Player 1" "X"] ["Player 2" "O"]] (board/new-board 3))
            (should= 6 @times-printed-board)
            (should= 5 @times-printed-piece)
            (should= 1 @times-exit-called)
            (should= '("3" "5" "2" "4" "1") @undo-stack)))))))
