(ns advent.day01.part1
  (:require [clojure.java.io :as io]))

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (reduce (fn [sum line]
              (+ sum (parse-long
                      (let [digits (re-seq #"\d" line)]
                        (str (first digits) (last digits))))))
            0
            (line-seq rdr))))

;; (run "../input/day01/input.txt")
;;=> 54331
