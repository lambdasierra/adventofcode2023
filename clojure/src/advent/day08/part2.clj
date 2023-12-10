(ns advent.day08.part2
  (:require [advent.day08.part1 :as part1]
            [clojure.string :as string]))

(defn start-label? [label]
  (string/ends-with? label "A"))

(defn end-label? [label]
  (string/ends-with? label "Z"))

(defn count-steps [instructions index]
  (loop [positions (filterv start-label? (keys index))
         instructions (cycle instructions)
         steps 0]
    (if (every? end-label? positions)
      steps
      (let [instruction (first instructions)]
        (recur (mapv #(get-in index [% instruction]) positions)
               (rest instructions)
               (inc steps))))))

(defn run [input-path]
  (let [{:keys [instructions network]} (part1/load-input input-path)]
    (count-steps instructions (part1/index-network network))))
