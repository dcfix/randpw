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
  []
  (+ 11111 (rand-int 55555)))

(def wordlists {:long   {:filename "resources/eff_large_wordlist.txt"
                         :sha256 "addd35536511597a02fa0a9ff1e5284677b8883b83e986e43f15a3db996b903e"}
                :short1 {:filename "resources/eff_short_wordlist_1.txt"
                         :sha256 "8f5ca830b8bffb6fe39c9736c024a00a6a6411adb3f83a9be8bfeeb6e067ae69"}
                :short2 {:filename "resources/eff_short_wordlist_2_0.txt"
                         :sha256 "22b45c52e0bd0bbf03aa522240b111eb4c7c0c1d86c4e518e1be2a7eb2a625e4"}})

(defn check-wordlist
  "make sure that the wordlist that we're using hasn't been modified"
  [wordlist]
  (= (digest/sha-256 (io/as-file (wordlist :filename)))
   (wordlist :sha256)))


(defn read-lines [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))

(defn make-entry
  "map the line into values"
  [line]
  (let [vals (str/split line #"\t")]
    (vector (keyword (first vals)) (second vals))))



(def mavals (apply hash-map (flatten (map make-entry (read-lines ((wordlists :short1) :filename))))))

; (:6665 mavals)
(:6666 mavals)


(hash-map (map make-entry (read-lines ((wordlists :short1) :filename))))


(rest mavals)


(hash-map "dude" "roy" "killer" "bob")
(str/split "1111\tdude" #"\t" )

(hash-map (keyword (first ["1111" "dude"])) (second ["1111" "dude"]))


(read-lines ((wordlists :short1) :filename))

((wordlists :long) :filename)

(get-number)

(get-word)


;; return 5 or 10 passwords, with numbers... have the user choose one of them?






(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
