(ns advent.day04.part2
  (:require [advent.day04.part1 :as part1]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(defn score [{:keys [winning have]}]
  (count (set/intersection winning have)))

(defn added-copies [{:keys [card score]} copies]
  (reduce
   (fn [m k]
     (assoc m (+ card k 1) (inc (get copies card 0))))
   {}
   (range score)))

(comment
  (added-copies {:card 1 :score 4} {})
  ;; => {2 1, 3 1, 4 1, 5 1}

  (added-copies {:card 2 :score 2} {2 1, 3 1, 4 1, 5 1})
  ;; => {3 2, 4 2}

  (merge-with +
              {2 1, 3 1, 4 1, 5 1}
              {3 2, 4 2})
  ;; => {2 1, 3 3, 4 3, 5 1}

  (added-copies {:card 3 :score 2} {2 1, 3 3, 4 3, 5 1})
  ;; => {4 4, 5 4}
)

(defn run-copies [input-path]
  (with-open [rdr (io/reader input-path)]
    (transduce
     (comp (map part1/parse-line)
           (map (fn [card] (assoc card :score (score card)))))
     (completing
      (fn [result card]
        (-> result
            (update :copies
                    (fn [copies]
                      (merge-with + copies (added-copies card copies))))
            (update :originals inc))))
     {:originals 0
      :copies {}}
     (line-seq rdr))))

(comment
  (run-copies "../input/day04/sample.txt")
  ;; => {:originals 6, :copies {2 1, 3 3, 4 7, 5 13}}

  (+ (:originals *1)
     (reduce + (vals (:copies *1))))
  ;; => 30
)

(defn run [input-path]
  (let [{:keys [originals copies]} (run-copies input-path)]
    (+ originals (reduce + (vals copies)))))

(comment
  (run "../input/day04/sample.txt")
  ;; => 30

  (run "../input/day04/input.txt")
  ;; => 23806951
)
