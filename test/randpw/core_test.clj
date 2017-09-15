(ns randpw.core-test
  (:require [clojure.test :refer :all]
            [randpw.core :refer :all]))

(deftest wordlist-checksum
  (check-wordlist (wordlists :long))
  (check-wordlist (wordlists :short1))
  (check-wordlist (wordlists :short2)))





