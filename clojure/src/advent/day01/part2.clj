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
  (re-pattern (str "^(" (string/join "|" (cons "\\d" (keys digit-names)))
                   ")")))

(defn parse-line [line]
  ;; Regular re-seq doesn't work because it consumes each match from
  ;; the input before looking for the next match. So a string
  ;; like "oneight" matches "one" but then only "ight" is left, so it
  ;; doesn't match "eight" at the end.
  (keep (fn [part]
          (when-let [[_ digit] (re-find digit-regex part)]
            (get digit-names digit digit)))
        (for [i (range (count line))]
          (subs line i))))

(defn combine-found-digits [digits]
  (parse-long (str (first digits) (last digits))))

(comment
  (parse-line "oneight")
  ;;=> (1 8)

  (parse-line "24oneightnineightfour")
  ;;=> ("2" "4" 1 8 9 8 4)

  (map (comp combine-found-digits
             parse-line)
       (line-seq
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

  (reduce + *1) ;;=> 281

  (parse-line "n5") ;;=> ("5")

  (parse-line "7bzcnthreejdh7oneightj")
  ;;=> ("7" 3 "7" 1 8)
)

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (reduce (fn [sum line]
              (+ sum (combine-found-digits (parse-line line))))
            0
            (line-seq rdr))))

(comment
  (run "../input/day01/input.txt")
  ;;=> 54518
  )

