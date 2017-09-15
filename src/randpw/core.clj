(ns randpw.core
  (:require [clojure.java.io :as io]
            [digest]
            [clojure.string :as str])
  (:gen-class))

;; what about 4 digit numbers?
(defn get-number []
  (format "%05d" (rand-int 100000)))

(defn get-word
  "the EFF dice list goes from 11111 to 66666"
  [die]
  (str/join (take die (repeatedly  #(+ 1 (rand-int 6))))))

(def wordlists {:long   {:filename "resources/eff_large_wordlist.txt"
                         :sha256 "addd35536511597a02fa0a9ff1e5284677b8883b83e986e43f15a3db996b903e"
                         :dice 5}
                :short1 {:filename "resources/eff_short_wordlist_1.txt"
                         :sha256 "8f5ca830b8bffb6fe39c9736c024a00a6a6411adb3f83a9be8bfeeb6e067ae69"
                         :dice 4}
                :short2 {:filename "resources/eff_short_wordlist_2_0.txt"
                         :sha256 "22b45c52e0bd0bbf03aa522240b111eb4c7c0c1d86c4e518e1be2a7eb2a625e4"
                         :dice 4}})

(defn check-wordlist
  "make sure that the wordlist that we're using hasn't been modified"
  [wordlist]
  (let [wl (wordlists wordlist)]
  (= (digest/sha-256 (io/as-file (wl :filename)))
   (wl :sha256))))


(defn read-lines [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

(defn make-entry
  "map the line into values"
  [line]
  (let [vals (str/split line #"\t")]
    (vector (keyword (first vals)) (second vals))))



(def mavals (apply hash-map (flatten (map make-entry (read-lines ((wordlists :long) :filename))))))

(defn generate-words
  [wordlist words-per-line]
  (let [words (apply hash-map (flatten (map make-entry (read-lines ((wordlists wordlist) :filename)))))
        dice ((wordlists wordlist) :dice)]
    (if (check-wordlist wordlist)
                        (str/join " " (map #(get words %) (take words-per-line (repeatedly #(keyword (str (get-word dice))))))))))





(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (str/join "\n" (repeatedly 20 #(generate-words :long 12 )))))



