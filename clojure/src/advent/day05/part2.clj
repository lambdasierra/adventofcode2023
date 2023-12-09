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
                               {:start start
                                :length length})))
                  (string/split (string/trim seed-strings) #"\s+"))}))

(defn parse-input [rdr]
  (let [[[seed-line] & mappings] (part1/split-on-blank-lines (line-seq rdr))]
    (assoc (parse-seeds seed-line)
           :mappings (mapv part1/parse-mapping mappings))))

(defn apply-mapping-range [input mapping-range]
  (let [{input-start :start input-length :length} input
        input-last (+ input-start input-length -1)
        {:keys [source-start target-start]
         mapping-length :length} mapping-range
        source-last (+ source-start mapping-length -1)]
    (cond
      ;; No overlap:
      ;; ...[ input ]................
      ;; ..............[ mapping ]...
      (< input-last source-start)
      {:found-mapping :none
       :remaining [{:start input-start
                    :length input-length}]}

      ;; Overlap on start of mapping:
      ;; ...[ input ]..........
      ;; ........[ mapping ]...
      (and (< input-start source-start)
           (< input-last source-last))
      (let [mapped-length (- input-last source-start)]
        {:found-mapping :overlap-start
         :mapped [{:start target-start
                   :length mapped-length}]
         :remaining [{:start input-start
                      :length (- mapping-length mapped-length)}]})

      ;; Complete overlap:
      ;; .....[ input ]....
      ;; ....[ mapping ]...
      (and (<= source-start input-start)
           (<= input-last source-last))
      {:found-mapping :overlap-all
       :mapped [{:start target-start
                 :length input-length}]}

      ;; Overlap on end of mapping:
      ;; .........[ input ]....
      ;; ...[ mapping ]........
      (and (< source-start input-start)
           (< source-last input-last))
      (let [mapped-length (- input-last source-start)]
        {:found-mapping :overlap-end
         :mapped [{:start target-start
                   :length mapped-length}]
         :remaining [{:start source-last
                      :length (- input-length mapped-length)}]})

      ;; No overlap:
      ;; ................[ input ]...
      ;; ...[ mapping ]..............
      (< source-last input-start)
      {:found-mapping :none
       :remaining [{:start input-start
                    :length input-length}]}

      :else
      :error-this-should-not-happen)))
