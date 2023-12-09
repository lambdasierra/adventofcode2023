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
  (let [{input-start :start
         input-length :length} input
        input-last (+ input-start input-length -1)
        {mapping-start :source-start
         target-start :target-start
         mapping-length :length} mapping-range
        mapping-last (+ mapping-start mapping-length -1)]
    (cond
      ;; No overlap:
      ;; ...[ input ]................
      ;; ..............[ mapping ]...
      (< input-last mapping-start)
      {:found-mapping :none
       :remaining [{:start input-start
                    :length input-length}]}

      ;; No overlap:
      ;; ................[ input ]...
      ;; ...[ mapping ]..............
      (< mapping-last input-start)
      {:found-mapping :none
       :remaining [{:start input-start
                    :length input-length}]}

      ;; Overlap on start of mapping:
      ;; ...[ input ]..........
      ;; ........[ mapping ]...
      (and (< input-start mapping-start)
           (< input-last mapping-last))
      (let [mapped-length (inc (- input-last mapping-start))]
        {:found-mapping :overlap-start
         :mapped [{:start target-start
                   :length mapped-length}]
         :remaining [{:start input-start
                      :length (- mapping-start input-start)}]})

      ;; Complete overlap:
      ;; .....[ input ]....
      ;; ....[ mapping ]...
      (and (<= mapping-start input-start)
           (<= input-last mapping-last))
      {:found-mapping :overlap-all
       :mapped [{:start target-start
                 :length input-length}]}

      ;; Overlap on end of mapping:
      ;; .........[ input ]....
      ;; ...[ mapping ]........
      (and (< mapping-start input-start)
           (< mapping-last input-last))
      (let [mapped-length (inc (- mapping-last input-start))]
        {:found-mapping :overlap-end
         :input-last input-last
         :mapping-last mapping-last
         :mapped [{:start target-start
                   :length mapped-length}]
         :remaining [{:start (+ input-start mapped-length)
                      :length (- input-last mapping-last)}]})

      :else
      :error-this-should-not-happen)))
