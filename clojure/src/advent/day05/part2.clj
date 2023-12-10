(ns advent.day05.part2
  (:require [advent.day05.part1 :as part1]
            [clojure.java.io :as io]
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

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (let [{:keys [seeds mappings]} (parse-input rdr)]
      (apply min (map (fn [seed] (part1/seed-to-location seed mappings))
                      (mapcat (fn [{:keys [start length]}]
                                (range start (+ start length)))
                              seeds))))))
