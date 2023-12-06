(ns advent.day04.part1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]
            [clojure.math :as math]))

(defn parse-line [line]
  (let [[_ card winning have] (re-matches #"Card\s+(\d+): ([\d\s]+) \| ([\d\s]+)" line)]
    {:card (parse-long card)
     :winning (mapv parse-long (string/split (string/trim winning) #"\s+"))
     :have (mapv parse-long (string/split (string/trim have) #"\s+"))}))

(comment
  (with-open [rdr (io/reader "../input/day04/sample.txt")]
    (into []
          (map parse-line)
          (line-seq rdr)))
  ;; => [{:card 1, :winning [41 48 83 86 17], :have [83 86 6 31 17 9 48 53]}
  ;;     {:card 2,
  ;;      :winning [13 32 20 16 61],
  ;;      :have [61 30 68 82 17 32 24 19]}
  ;;     {:card 3, :winning [1 21 53 59 44], :have [69 82 63 72 16 21 14 1]}
  ;;     {:card 4, :winning [41 92 73 84 69], :have [59 84 76 51 58 5 54 83]}
  ;;     {:card 5,
  ;;      :winning [87 83 26 28 32],
  ;;      :have [88 30 70 12 93 22 82 36]}
  ;;     {:card 6,
  ;;      :winning [31 18 13 56 72],
  ;;      :have [74 77 10 23 35 67 36 11]}]
)

(defn score [{:keys [winning have]}]
  (let [c (count (set/intersection (set winning) (set have)))]
    (if (zero? c)
      0
      (long (math/pow 2 (dec c))))))

(comment
  (with-open [rdr (io/reader "../input/day04/sample.txt")]
    (into []
          (comp (map parse-line)
                (map #(assoc % :score (score %))))
          (line-seq rdr)))
  ;; => [{:card 1,
  ;;      :winning [41 48 83 86 17],
  ;;      :have [83 86 6 31 17 9 48 53],
  ;;      :score 8}
  ;;     {:card 2,
  ;;      :winning [13 32 20 16 61],
  ;;      :have [61 30 68 82 17 32 24 19],
  ;;      :score 2}
  ;;     {:card 3,
  ;;      :winning [1 21 53 59 44],
  ;;      :have [69 82 63 72 16 21 14 1],
  ;;      :score 2}
  ;;     {:card 4,
  ;;      :winning [41 92 73 84 69],
  ;;      :have [59 84 76 51 58 5 54 83],
  ;;      :score 1}
  ;;     {:card 5,
  ;;      :winning [87 83 26 28 32],
  ;;      :have [88 30 70 12 93 22 82 36],
  ;;      :score 0}
  ;;     {:card 6,
  ;;      :winning [31 18 13 56 72],
  ;;      :have [74 77 10 23 35 67 36 11],
  ;;      :score 0}]

  )

(comment
  (with-open [rdr (io/reader "../input/day04/sample.txt")]
    (transduce
     (comp (map parse-line)
           (map score))
     + 0
     (line-seq rdr)))
  ;; => 13

)

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (transduce
     (comp (map parse-line)
           (map score))
     + 0
     (line-seq rdr))))

(comment
  (run "../input/day04/input.txt")
  ;; => 20407
)
