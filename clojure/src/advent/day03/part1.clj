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
                  (recur acc 0 (inc y))

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
