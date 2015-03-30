(ns clojure-ttt.console-io-spec
  (:require [speclj.core :refer :all]
            [clojure-ttt.console-io :refer :all]))

(describe "console-io"
  (around [it]
    (with-out-str (it)))
  (describe "#prompt"
    (it "tests the input"
      (should= "1"
               (with-in-str "1"
                 (prompt "enter some amount"))))
    (it "tests the output"
      (should= "enter some amount\n"
               (with-out-str (with-in-str "1"
                               (prompt "enter some amount"))))))
  (describe "#print-message"
    (it "tests the output"
      (should= "hello world\n"
               (with-out-str (print-message "hello world"))))))

