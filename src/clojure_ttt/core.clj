(ns clojure-ttt.core
  (:require [clojure-ttt.ttt-game :as game]
            [clojure-ttt.board.board :as board]
            [clojure-ttt.ui :as ui]))

(defn -main
  [& args]
  (ui/print-winner (game/play [["Player 1" "X"] ["Computer 2" "O"]] (board/new-board 3))))
