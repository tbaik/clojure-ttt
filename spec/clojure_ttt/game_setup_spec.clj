(ns clojure-ttt.game-setup-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.game-setup :refer :all]
            [clojure-ttt.ui :as ui]))

(defn make-input [coll]
  (apply str (interleave coll (repeat "\n"))))

(describe "game-setup"
  (around [it]
    (with-out-str (it)))

  (describe "#build-opponent"
    (it "returns the string Computer if given 1 as input"
      (should= "Computer"
               (with-in-str "1"
                 (build-opponent))))
    (it "returns the string Player if given 2 as input"
      (should= "Player 2"
               (with-in-str "2"
                 (build-opponent))))
    (it "returns an invalid input error for any other input and tries again"
      (let [times-error-printed (atom 0)]
        (with-redefs [ui/print-invalid-input-error (fn [] (swap! times-error-printed inc))]
          (should= 0 @times-error-printed)
          (with-in-str (make-input '("whatever" "1")) (build-opponent))
          (should= 1 @times-error-printed)))))

  (describe "#build-turn"
    (it "returns X if given 1 as input"
      (should= "X"
               (with-in-str "1"
                 (build-turn))))
    (it "returns O if given 2 as input"
      (should= "O"
               (with-in-str "2"
                 (build-turn))))
    (it "exits program given 3 as input"
      (let [times-exit-called (atom 0)]
        (with-redefs [exit-system (fn [] (swap! times-exit-called inc))]
          (should= 0 @times-exit-called)
          (with-in-str "3"
            (build-turn))
          (should= 1 @times-exit-called))))
    (it "returns an invalid input error for any other input and tries again"
      (let [times-error-printed (atom 0)]
        (with-redefs [ui/print-invalid-input-error (fn [] (swap! times-error-printed inc))]
          (should= 0 @times-error-printed)
          (with-in-str (make-input '("whatever" "1")) (build-turn))
          (should= 1 @times-error-printed)))))

  (describe "#create-players"
    (it "returns a vector with player 1 vs ai with X and O piece given 1 and 1 input"
      (should= [["Player 1" "X"]["Computer" "O"]]
               (with-in-str (make-input '("1" "1")) (create-players))))
    (it "returns a vector with player 2 vs player 1 with X and O piece given 2 and 2 input"
      (should= [["Player 2" "X"]["Player 1" "O"]]
               (with-in-str (make-input '("2" "2")) (create-players))))))

