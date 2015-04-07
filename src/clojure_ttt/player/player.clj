(ns clojure-ttt.player.player
  (:require [clojure-ttt.player.human-player :as human]
            [clojure-ttt.player.hard-ai :as ai]
            [clojure-ttt.player.players :as players]
            [clojure-ttt.board.board :as board]))

(defn choose-move [players board]
  (if (players/is-player-turn players)
    (human/receive-human-move (players/current-player-name players) board)
    (ai/best-ai-move players board)))

(defn undo-turn [undo-stack board]
  (board/undo-move (peek (pop undo-stack))
             (board/undo-move (peek undo-stack) board)))

(defn undo-move [undo-stack board]
  (board/undo-move (peek undo-stack) board))
