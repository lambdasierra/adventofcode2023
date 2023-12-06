(ns advent.day04.part1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]
            [clojure.math :as math]))

(defn parse-numbers [numbers-str]
  (into #{} (map parse-long) (string/split (string/trim numbers-str) #"\s+")))

(defn parse-line [line]
  (let [[_ card winning have] (re-matches #"Card\s+(\d+): ([\d\s]+) \| ([\d\s]+)" line)]
    {:card (parse-long card)
     :winning (parse-numbers winning)
     :have (parse-numbers have)}))

(comment
  (with-open [rdr (io/reader "../input/day04/sample.txt")]
    (into []
          (map parse-line)
          (line-seq rdr)))
  ;; => [{:card 1,
  ;;      :winning #{86 48 41 17 83},
  ;;      :have #{86 48 31 6 17 9 83 53}}
  ;;     {:card 2,
  ;;      :winning #{20 32 13 61 16},
  ;;      :have #{24 32 61 17 82 19 68 30}}
  ;;     {:card 3,
  ;;      :winning #{59 1 21 44 53},
  ;;      :have #{72 1 69 21 82 14 16 63}}
  ;;     {:card 4,
  ;;      :winning #{69 92 41 73 84},
  ;;      :have #{59 58 54 51 76 5 83 84}}
  ;;     {:card 5,
  ;;      :winning #{32 28 83 26 87},
  ;;      :have #{70 88 22 36 93 12 82 30}}
  ;;     {:card 6,
  ;;      :winning #{72 31 56 13 18},
  ;;      :have #{74 77 36 23 35 11 10 67}}]
)

(defn score [{:keys [winning have]}]
  (let [c (count (set/intersection winning have))]
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
  ;;      :winning #{86 48 41 17 83},
  ;;      :have #{86 48 31 6 17 9 83 53},
  ;;      :score 8}
  ;;     {:card 2,
  ;;      :winning #{20 32 13 61 16},
  ;;      :have #{24 32 61 17 82 19 68 30},
  ;;      :score 2}
  ;;     {:card 3,
  ;;      :winning #{59 1 21 44 53},
  ;;      :have #{72 1 69 21 82 14 16 63},
  ;;      :score 2}
  ;;     {:card 4,
  ;;      :winning #{69 92 41 73 84},
  ;;      :have #{59 58 54 51 76 5 83 84},
  ;;      :score 1}
  ;;     {:card 5,
  ;;      :winning #{32 28 83 26 87},
  ;;      :have #{70 88 22 36 93 12 82 30},
  ;;      :score 0}
  ;;     {:card 6,
  ;;      :winning #{72 31 56 13 18},
  ;;      :have #{74 77 36 23 35 11 10 67},
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
