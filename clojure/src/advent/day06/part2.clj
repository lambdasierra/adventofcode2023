(ns advent.day06.part2
  (:require [advent.day06.part1 :as part1]
            [clojure.java.io :as io]))

(defn parse-digits [string]
  (parse-long (apply str (re-seq #"\d" string))))

(defn load-input [input-path]
  (with-open [rdr (io/reader input-path)]
    (let [[times-line distance-line] (line-seq rdr)]
      {:time (parse-digits times-line)
       :distance (parse-digits distance-line)})))

(defn run [input-path]
  (count (part1/winning-options (load-input input-path))))
