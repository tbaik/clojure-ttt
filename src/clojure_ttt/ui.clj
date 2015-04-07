(ns clojure-ttt.ui
  (:require [clojure-ttt.board.board-presenter :as board-presenter]
            [clojure-ttt.console-io :as io]))

(defn prompt-new-move [player-name]
  (io/prompt (str "Here is the Game Board, " player-name ". Please enter an empty space number or type U to undo the last turn.")))

(defn print-invalid-move-error []
  (io/print-message "Invalid move! Try again."))

(defn print-invalid-input-error []
  (io/print-message "Invalid input! Try again."))

(defn print-undo-stack-empty-error []
  (io/print-message "There are no moves to undo!"))

(defn print-placed-piece [move player]
  (io/print-message (str (first player) " placed " (last player) " at " move)))

(defn print-board [board]
  (io/print-message (board-presenter/board-string board)))

(defn print-winner [winner]
  (if (= winner "tie")
    (io/print-message "It's a tie!")
    (io/print-message (str winner " won!"))))

(defn prompt-human-or-ai []
  (io/prompt "Please type 1 to play against a Computer Player or 2 to play against another Person."))

(defn prompt-turn []
  (io/prompt "Type 1 to go First(X), 2 to go Second(O), or 3 to exit."))

(defn prompt-game-over-options []
  (io/prompt "Type 1 to start a new game, 2 to Undo last move, or 3 to exit."))
