(ns clojure-ttt.player.human-player-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.player.human-player :refer :all]
            [clojure-ttt.ui :as ui]
            [clojure-ttt.board.board :as board]))

(describe "human-player"
  (describe "#receive-human-move"
    (it "returns a string for move to place if valid input on board"
      (should= "1"
               (with-redefs [ui/prompt-new-move (fn [_] "1")]
                 (receive-human-move "Player 1" (board/new-board 3)))))))

