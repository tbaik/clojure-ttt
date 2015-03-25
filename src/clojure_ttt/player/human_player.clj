(ns clojure-ttt.player.human-player
  (:require [clojure-ttt.ttt-rules :as rules]
            [clojure-ttt.ui :as ui]))

(defn receive-human-move [player-name board]
  (let [move (ui/prompt-new-move player-name)]
    (if (rules/is-valid-move? move board)
      move
      (do
        (ui/print-invalid-move-error)
        (receive-human-move player-name board)))))

