(ns clojure-ttt.player.players)

(defn current-player-piece [players]
  (last (first players)))

(defn current-player-name [players]
  (first (first players)))

(defn is-player-turn [players]
  (.startsWith (current-player-name players) "Player"))


