(ns advent.day08.part1
  (:require [clojure.java.io :as io]))

(defn parse-instructions [line]
  (mapv {\L :left \R :right} line))

(defn parse-node [line]
  (let [[_ label left right] (re-matches #"^(\w+) = \((\w+), (\w+)\)$" line)]
    {:label label :left left :right right}))

(defn parse-lines [lines]
  (let [[instruction-line _ & node-lines] lines]
    {:instructions (parse-instructions instruction-line)
     :network (mapv parse-node node-lines)}))

(defn load-input [input-path]
  (with-open [rdr (io/reader input-path)]
    (parse-lines (line-seq rdr))))

(defn index-network [network]
  (reduce (fn [index {:keys [label] :as node}]
            (assoc index label node))
          {}
          network))

(defn count-steps [instructions index]
  (loop [position "AAA"
         instructions (cycle instructions)
         steps 0]
    (if (= position "ZZZ")
      steps
      (recur (get-in index [position (first instructions)])
             (rest instructions)
             (inc steps)))))

(defn run [input-path]
  (let [{:keys [instructions network]} (load-input input-path)]
    (count-steps instructions (index-network network))))
