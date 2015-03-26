(ns clojure-ttt.player.hard-ai-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.player.hard-ai :refer :all]))

(describe "hard-ai"
  (describe "#game-evaluation"
    (it "returns 1 if has no winner"
      (should= 1
               (let [has-winner false is-player-turn false]
                 (game-evaluation has-winner is-player-turn))))
    (it "returns 2 if there is a winner and is player turn"
      (should= 2
               (let [has-winner true is-player-turn true]
                 (game-evaluation has-winner is-player-turn))))
    (it "returns 0 if there is a winner and is not player turn"
      (should= 0
               (let [has-winner true is-player-turn false]
                 (game-evaluation has-winner is-player-turn)))))

  (describe "#minimax"
    (it "returns 1 if board is full with out a winner"
      (should= 1
               (minimax [["Player 1" "X"]["Computer 2" "O"]]
                        ["X" "O" "X" "O" "O" "X" "X" "X" "O"])))
    (it "returns 2 if has winner and current player is human"
      (should= 2
               (minimax [["Player 1" "X"]["Computer 2" "O"]]
                        ["X" "X" "X" "O" "O" 6 7 8 9])))
    (it "returns 0 if has winner and curent player is computer"
      (should= 0
               (minimax [["Computer 2" "O"]["Player 1" "X"]]
                        ["O" "O" "O" "X" "X" 6 7 8 9])))
    (it "returns an eval of 2 for a game you can't lose"
      (should= 2
               (minimax [["Computer 2" "O"]["Player 1" "X"]]
                        [1 "X" 3 4 "O" 6 7 "X" 9])))
    (it "returns an eval of 1 for a maximum tied game"
      (should= 1
               (minimax [["Computer 2" "O"]["Player 1" "X"]]
                        ["X" 2 3 4 5 6 7 8 9])))
    (it "returns an eval of 0 for a game bound to lose"
      (should= 0
               (minimax [["Computer 2" "O"]["Player 1" "X"]]
                        ["X" "O" 3 4 "X" 6 7 8 9]))))

  (describe "#get-scores"
    (it "returns a coll of score evaluations from available moves"
      (should= [0 2 1 1 2 1 2]
               (get-scores [["Computer 2" "O"]["Player 1" "X"]]
                           ["O" 2 "X" 4 5 6 7 8 9]))))

  (describe "#best-ai-move"
    (it "returns the highest score's move from all of the minimax results"
      (should= "5"
               (best-ai-move [["Computer 2" "O"]["Player 1" "X"]]
                             ["X" 2 3 4 5 6 7 8 9])))))

