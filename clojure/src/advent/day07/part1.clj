(ns advent.day07.part1
  (:require [clojure.java.io :as io]))

(def card-labels "AKQJT98765432")

(def card-value
  (zipmap card-labels
          (reverse (range (count card-labels)))))

(defn hand-type [hand]
  (let [label-counts (sort (vals (frequencies hand)))]
    (case label-counts
      [5] :five-of-a-kind
      [1 4] :four-of-a-kind
      [2 3] :full-house
      [1 1 3] :three-of-a-kind
      [1 2 2] :two-pair
      [1 1 1 2] :one-pair
      [1 1 1 1 1] :high-card)))

(def hand-types
  [:five-of-a-kind :four-of-a-kind :full-house
   :three-of-a-kind :two-pair :one-pair :high-card])

(def hand-type-value
  (zipmap hand-types
          (reverse (range (count hand-types)))))

(defn compare-hands [hand-a hand-b]
  (let [c (compare (hand-type-value (hand-type hand-a))
                   (hand-type-value (hand-type hand-b)))]
    (if (zero? c)
      (compare (mapv card-value hand-a)
               (mapv card-value hand-b))
      c)))

(defn parse-line [line]
  (let [[_ hand bid] (re-matches #"^([AKQJT98765432]+) (\d+)$" line)]
    {:hand hand
     :bid (parse-long bid)}))

(defn load-input [input-path]
  (with-open [rdr (io/reader input-path)]
    (mapv parse-line (line-seq rdr))))

(defn run [input-path]
  (let [hands-bids (load-input input-path)]
    (reduce +
            (map (fn [{:keys [bid]} rank]
                   (* bid rank))
                 (sort-by :hand compare-hands hands-bids)
                 (range 1 (inc (count hands-bids)))))))
