(ns clojure-ttt.core
  (:require [clojure-ttt.ttt-game :as game]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.game-setup :as setup]
            [clojure-ttt.ui :as ui]))

(defn -main
  [& args]
  (while true
    (game/reset-undo-stack)
    (ui/print-winner (game/play (setup/create-players) (board/new-board 3)))))
