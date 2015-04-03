(ns clojure-ttt.core-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.core :as core]
            [clojure.java.io :as io]))

;(defn make-input [coll]
  ;(apply str (interleave coll (repeat "\n"))))

;(describe "integration tests"
  ;(describe "#gameloop"
    ;(it "Go first as X, play against human, print X as winner after 5 moves"
      ;(spit "integration-test.txt"
            ;(with-out-str
              ;(with-in-str
                ;(make-input '(1 2 1 4 2 5 3))
                ;(core/gameloop))))
      ;(should-contain "X won!" (slurp "integration-test.txt"))
      ;(io/delete-file "integration-test.txt"))))
