(ns advent.day05.part2
  (:require [advent.day05.part1 :as part1]
            [clojure.java.io :as io]
            [clojure.math :as math]
            [clojure.string :as string]))

(defn parse-seeds [line]
  (let [[_ seed-strings] (re-matches #"^seeds: ([\d ]+)" line)]
    {:seeds (into []
                  (comp (map parse-long)
                        (partition-all 2)
                        (map (fn [[start length]]
                               {:start start :length length})))
                  (string/split (string/trim seed-strings) #"\s+"))}))

(defn parse-input [rdr]
  (let [[[seed-line] & mappings] (part1/split-on-blank-lines (line-seq rdr))]
    (assoc (parse-seeds seed-line)
           :mappings (mapv part1/parse-mapping mappings))))

(defn apply-mapping [input-range mapping-range]
  (let [{input-start :start
         input-length :length} input-range
        input-end (+ input-start input-length)
        {mapping-start :source-start
         mapping-length :length} mapping-range
        mapping-end (+ mapping-start mapping-length)
        overlap-start (max input-start mapping-start)
        overlap-end (min input-end mapping-end)
        overlap-length (- overlap-end overlap-start)
        mapped-start (+ (:target-start mapping-range)
                        (max 0 (- input-start mapping-start)))
        mapped-end (+ mapped-start overlap-length)]
    (when (< input-start mapping-start)
      (printf "Non-overlap before: %d to %d (length %d)%n"
              input-start mapping-start (- mapping-start input-start)))
    (when (< mapping-end input-end)
      (printf "Non-overlap after:  %d to %d (length %d)%n"
              mapping-end input-end (- input-end mapping-end)))

    (when (pos? overlap-length)
      (printf "Overlap: %d to %d (length %d)%n"
              overlap-start overlap-end overlap-length)
      (printf "Mapped:  %d to %d (length %d)%n"
              mapped-start mapped-end overlap-length))))

(defn apply-all-mappings [input-range mapping-ranges]
  (loop [input-range input-range
         mapping-ranges mapping-ranges
         mapped-ranges []]
    (cond
      ;; No inputs left, we're done:
      (zero? (:length input-range))
      mapped-ranges

      ;; More mapping ranges to apply:
      (seq mapping-ranges)
      (let [{:keys [mapped remaining]}
            (apply-mapping input-range
                           (first mapping-ranges))]
        (recur remaining
               (rest mapping-ranges)
               (into mapped mapping-ranges)))

      ;; No more mapping ranges to apply,
      ;; copy the remaining numbers to the output:
      :else
      (conj mapped-ranges input-range))))
