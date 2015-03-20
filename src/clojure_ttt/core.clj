(ns clojure-ttt.core
(:require [clojure-ttt.ttt-game :as game]
            [clojure-ttt.board :as board]))

(defn -main
  [& args]
  (game/play [["Player 1" "O"] ["Player 2" "X"]] (board/new-board 3)))
