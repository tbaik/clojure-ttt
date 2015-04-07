(ns clojure-ttt.core
  (:require [clojure-ttt.ttt-game :as game]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.game-setup :as setup]
            [clojure-ttt.ui :as ui]))

(defn gameloop []
  (game/reset-undo-stack)
  (game/play (setup/create-players) (board/new-board 3)))

(defn -main [& args]
  (while true
    (gameloop)))
