(ns advent.day09.part1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn parse-line [line]
  (mapv parse-long (string/split line #"\s+")))

(defn load-input [input-path]
  (with-open [rdr (io/reader input-path)]
    (into []
          (map parse-line)
          (line-seq rdr))))

(defn differences [numbers]
  (mapv (fn [[a b]] (- b a))
        (partition 2 1 numbers)))

(defn expand [number-lists]
  (lazy-seq
   (let [numbers (first number-lists)]
     (if (every? zero? numbers)
       (rest number-lists)
       (expand (cons (differences numbers) number-lists))))))

(defn contract [number-lists n]
  (lazy-seq
   (when (seq number-lists)
     (let [numbers (first number-lists)
           x (+ (peek numbers) n)]
       (cons (conj numbers x)
             (contract (rest number-lists) x))))))

(defn final-value [numbers]
  (peek (last (contract (expand (list numbers)) 0))))

(defn run [input-path]
  (transduce (map final-value)
             +
             0
             (load-input input-path)))
