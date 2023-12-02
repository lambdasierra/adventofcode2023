(ns advent.day01.part2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))


(def digit-names
  {"one"   1
   "two"   2
   "three" 3
   "four"  4
   "five"  5
   "six"   6
   "seven" 7
   "eight" 8
   "nine"  9})

(def digit-regex
  (re-pattern (string/join "|" (cons "\\d" (keys digit-names)))))

(defn parse-line [line]
  (parse-long
   (let [digits (map (fn [digit]
                       (get digit-names digit digit))
                     (re-seq digit-regex line))]
     (str (first digits) (last digits)))))

(comment
  (map parse-line (line-seq
                   (java.io.BufferedReader.
                    (java.io.StringReader.
                     "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen"))))
  ;;=>
  (29 83 13 24 42 14 76)

  (reduce + *1)
  ;;=>
  281

  (parse-line "n5") ;;=>55

  (parse-line "7bzcnthreejdh7oneightj") ;;=> 71
  (re-seq digit-regex "7bzcnthreejdh7oneightj"))

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (reduce (fn [sum line]
              (+ sum (parse-line line)))
            0
            (line-seq rdr))))

(comment
  (run "../input/day01/input.txt")
  ;;=> 54533
  )

