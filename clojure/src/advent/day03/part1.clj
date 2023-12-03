(ns advent.day03.part1
  (:require [clojure.java.io :as io]))

(defn scan-input
  "Returns a reducible which reads the input file
  and produces a series of maps.

  Each map contains the following keys:

    :x - x-coordinate, zero-indexed
    :y - y-coordinate, zero-indexed
    :character - the Character at that x,y position
  "
  [input-path]
  (reify clojure.lang.IReduceInit
    (reduce [_this f init]
      (with-open [rdr (io/reader input-path)]
        (loop [acc init
               x 0
               y 0]
          (let [char-int (.read rdr)]
            (cond (or (neg? char-int)   ; end-of-file
                      (reduced? acc))
                  (unreduced acc)

                  (= 10 char-int)       ; newline
                  (recur (f acc {:character \newline})
                         0
                         (inc y))

                  :else
                  (recur (f acc {:x x :y y :character (char char-int)})
                         (inc x)
                         y))))))))

(comment
  (into [] (scan-input "../input/day03/sample.txt"))
  ;; => [{:x 0, :y 0, :character \4}
  ;;     {:x 1, :y 0, :character \6}
  ;;     {:x 2, :y 0, :character \7}
  ;;     {:x 3, :y 0, :character \.}
  ;;     {:x 4, :y 0, :character \.}
  ;;     ...

  )

(defn input-is-symbol? [{:keys [character]}]
  (not (or (Character/isDigit character)
           (= \. character)
           (= \newline character))))

(defn input-to-point [{:keys [x y]}]
  [x y])

(defn find-symbols
  "First pass: Returns a set of [x,y] pairs
  for all symbol characters in the input."
  [input]
  (into #{}
        (comp (filter input-is-symbol?)
              (map input-to-point))
        input))

(comment
  (into [] (scan-input "../input/day03/sample.txt"))
  ;; => [{:x 0, :y 0, :character \4}
  ;;     {:x 1, :y 0, :character \6}
  ;;     {:x 2, :y 0, :character \7}
  ;;     {:x 3, :y 0, :character \.}
  ;;     {:x 4, :y 0, :character \.}
  ;;     {:x 5, :y 0, :character \1}
  ;;     {:x 6, :y 0, :character \1}
  ;;     {:x 7, :y 0, :character \4}
  ;;     {:x 8, :y 0, :character \.}
  ;;     {:x 9, :y 0, :character \.}
  ;;     {:character \newline}
  ;;     {:x 0, :y 1, :character \.}
  ;;     {:x 1, :y 1, :character \.}
  ;;     ...

  (into []
        (filter input-is-symbol?)
        (scan-input "../input/day03/sample.txt"))
  ;; => [{:x 3, :y 1, :character \*}
  ;;     {:x 6, :y 3, :character \#}
  ;;     {:x 3, :y 4, :character \*}
  ;;     {:x 5, :y 5, :character \+}
  ;;     {:x 3, :y 8, :character \$}
  ;;     {:x 5, :y 8, :character \*}]

  (find-symbols (scan-input "../input/day03/sample.txt"))
  ;; => #{[6 3] [3 4] [5 8] [5 5] [3 1] [3 8]}

  )

(defn find-numbers
  "Returns a transducer that parses inputs from `scan-input` and
  produces a series of maps for each number found in the input.

  Each map contains the following keys:

    :number - the parsed number as a long

    :positions - a collection of [x,y] coordinate pairs
                 for all digits in that number"
  []
  (fn [rfn]
    (let [digits (volatile! [])
          positions (volatile! [])]
      (fn
        ([] (rfn))
        ([result] (rfn result))
        ([result {:keys [x y character]}]
         (if (Character/isDigit character)
           ;; We found a digit:
           (do (vswap! digits conj character)
               (vswap! positions conj [x y])
               result)
           (if (empty? @digits)
             ;; Nothing happened:
             result
             ;; We have finished reading digits:
             (let [return-number (parse-long (apply str @digits))
                   return-positions @positions]
               (vreset! digits [])
               (vreset! positions [])
               (rfn result {:number return-number
                            :positions return-positions})))))))))

(comment
  (into [] (find-numbers) (scan-input "../input/day03/sample.txt"))
  ;; => [{:number 467, :positions [[0 0] [1 0] [2 0]]}
  ;;     {:number 114, :positions [[5 0] [6 0] [7 0]]}
  ;;     {:number 35, :positions [[2 2] [3 2]]}
  ;;     {:number 633, :positions [[6 2] [7 2] [8 2]]}
  ;;     {:number 617, :positions [[0 4] [1 4] [2 4]]}
  ;;     {:number 58, :positions [[7 5] [8 5]]}
  ;;     {:number 592, :positions [[2 6] [3 6] [4 6]]}
  ;;     {:number 755, :positions [[6 7] [7 7] [8 7]]}
  ;;     {:number 664, :positions [[1 9] [2 9] [3 9]]}
  ;;     {:number 598, :positions [[5 9] [6 9] [7 9]]}]

  )
