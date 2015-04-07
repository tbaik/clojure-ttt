(ns clojure-ttt.player.player-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.player.player :refer :all]
            [clojure-ttt.player.hard-ai :as ai]
            [clojure-ttt.player.human-player :as human]))

(describe "player"
  (describe "#choose-move"
    (it "asks human for move if it's player's turn"
      (let [times-asked-human-move (atom 0)]
        (with-redefs [human/receive-human-move (fn [_ _] (swap! times-asked-human-move inc))]
          (should= 0 @times-asked-human-move)
          (choose-move [["Player 1" "X"]["Computer 2" "O"]] [1 2 3 4 5 6 7 8 9])
          (should= 1 @times-asked-human-move))))
    (it "calls on ai's best move method if computer's turn"
      (let [times-asked-ai-move (atom 0)]
        (with-redefs [ai/best-ai-move (fn [_ _] (swap! times-asked-ai-move inc))]
          (should= 0 @times-asked-ai-move)
          (choose-move [["Computer 2" "O"]["Player 1" "X"]] [1 2 3 4 5 6 7 8 9])
          (should= 1 @times-asked-ai-move)))))

  (describe "#undo-turn"
    (it "returns a board with the last two moves from the stack undone"
      (should= [1 2 "O" 4 "X" 6 7 8 9]
               (undo-turn '("1" "9" "3" "5") ["X" 2 "O" 4 "X" 6 7 8 "O"]))))

  (describe "#undo-move"
    (it "returns a board with the last move undone"
      (should= [1 2 "O" 4 "X" 6 7 8 "O"]
               (undo-move '("1" "9" "3" "5") ["X" 2 "O" 4 "X" 6 7 8 "O"])))))


