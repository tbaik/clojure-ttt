(ns clojure-ttt.player.players-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.player.players :refer :all]))

(describe "players"
  (describe "#current-player-piece"
    (it "returns the piece of the current player"
      (should= "X"
               (current-player-piece [["Player 1" "X"]["Player 2" "O"]]))))

  (describe "#current-player-name"
    (it "returns the name of current player"
      (should= "Player 1"
               (current-player-name [["Player 1" "X"]["Player 2" "O"]]))))

  (describe "#is-player-turn"
    (it "returns true if current player has Player in the name"
      (should= true
               (is-player-turn [["Player 1" "X"]["Computer 2" "O"]])))
    (it "returns false if not human"
      (should= false
               (is-player-turn [["Computer 1" "X"]["Player 2" "O"]])))))
