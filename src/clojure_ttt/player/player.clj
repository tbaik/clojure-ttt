(ns clojure-ttt.player.player
  (:require [clojure-ttt.player.human-player :as human]
            [clojure-ttt.player.hard-ai :as ai]
            [clojure-ttt.player.players :as players]))

(defn choose-move [players board]
  (if (players/is-player-turn players)
    (human/receive-human-move (players/current-player-name players) board)
    (ai/best-ai-move players board)))

