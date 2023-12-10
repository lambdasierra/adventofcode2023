(ns advent.day07.part2
  (:require [advent.day07.part1 :as part1]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def non-joker-card-labels
  (string/replace part1/card-labels "J" ""))

(def card-value (assoc part1/card-value \J -1))

(defn hand-type [hand]
  (apply max-key
         part1/hand-type-value
         (map (fn [substitution]
                (part1/hand-type (string/replace hand \J substitution)))
              non-joker-card-labels)))

(defn compare-hands [hand-a hand-b]
  (let [c (compare (part1/hand-type-value (hand-type hand-a))
                   (part1/hand-type-value (hand-type hand-b)))]
    (if (zero? c)
      (compare (mapv card-value hand-a)
               (mapv card-value hand-b))
      c)))

(defn run [input-path]
  (let [hands-bids (part1/load-input input-path)]
    (reduce +
            (map (fn [{:keys [bid]} rank]
                   (* bid rank))
                 (sort-by :hand compare-hands hands-bids)
                 (range 1 (inc (count hands-bids)))))))
