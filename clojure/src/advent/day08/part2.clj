(ns advent.day08.part2
  (:require [advent.day08.part1 :as part1]
            [clojure.string :as string]))

(defn start-label? [label]
  (string/ends-with? label "A"))

(defn end-label? [label]
  (string/ends-with? label "Z"))

(defn count-steps [instructions index start-label]
  (loop [position start-label
         instructions (cycle instructions)
         steps 0]
    (if (end-label? position)
      steps
      (recur (get-in index [position (first instructions)])
             (rest instructions)
             (inc steps)))))

(defn gcd
  "Greatest common divisor"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm
  "Least common multiple"
  [a b]
  (* (abs a) (/ (abs b) (gcd a b))))

(defn run [input-path]
  (let [{:keys [instructions network]} (part1/load-input input-path)
        index (part1/index-network network)
        starting-nodes (filter start-label? (keys index))]
    (reduce lcm (map #(count-steps instructions index %) starting-nodes))))
