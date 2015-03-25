(ns clojure-ttt.players-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.players :refer :all]
            [clojure-ttt.board :as board]))

(describe "players"
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

  (describe "#is-player-turn"
    (it "returns true if current player has Player in the name"
      (should= true
               (is-player-turn [["Player 1" "X"]["Computer 2" "O"]])))
    (it "returns false if not human"
      (should= false
               (is-player-turn [["Computer 1" "X"]["Player 2" "O"]]))))

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
                        ["X" "O" 3 4 "X" 6 7 8 9])))))

;(describe "#best-ai-move"
;(it "")))


