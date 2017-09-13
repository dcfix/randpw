(ns randpw.core-test
  (:require [clojure.test :refer :all]
            [randpw.core :refer :all]))

(deftest wordlist-checksum
  (check-wordlist (wordlists :long))
  (check-wordlist (wordlists :short1))
  (check-wordlist (wordlists :short2))
  (= false (check-wordlist (wordlists :short2))))

(deftest wordlist-bad-checksum
  (not (check-wordlist {:filename "resources/eff_large_wordlist.txt"
                        :sha256 "this is an obviously bad checksum"})))




