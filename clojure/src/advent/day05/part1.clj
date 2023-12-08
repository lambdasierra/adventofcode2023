(ns advent.day05.part1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn parse-seeds [line]
  (let [[_ seed-strings] (re-matches #"^seeds: ([\d ]+)" line)]
    {:seeds (mapv parse-long (string/split (string/trim seed-strings) #"\s+"))}))

(defn parse-mapping [lines]
  (let [[heading & mapping-lines] lines
        [_ source target] (re-matches #"^(\w+)-to-(\w+) map:$" heading)
        ranges (mapv (fn [line]
                         (let [[target-range-start
                                source-range-start
                                range-length] (map parse-long (string/split line #"\s+"))]
                           {:target-start target-range-start
                            :source-start source-range-start
                            :length range-length}))
                       mapping-lines)]
    {:source source
     :target target
     :ranges ranges}))

(defn split-on-blank-lines [lines]
  (->> lines
       (partition-by string/blank?)
       (remove #(= '("") %))))

(defn parse-input [rdr]
  (let [[[seed-line] & mappings] (split-on-blank-lines (line-seq rdr))]
    (assoc (parse-seeds seed-line)
           :mappings (mapv parse-mapping mappings))))
