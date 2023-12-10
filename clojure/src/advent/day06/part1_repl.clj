(in-ns 'advent.day06.part1)

(require '[clojure.math :as math])

(load-input "../input/day06/sample.txt")
;; => [{:time 7, :distance 9}
;;     {:time 15, :distance 40}
;;     {:time 30, :distance 200}]

(load-input "../input/day06/input.txt")
;; => [{:time 35, :distance 212}
;;     {:time 93, :distance 2060}
;;     {:time 73, :distance 1201}
;;     {:time 66, :distance 1044}]

(math/round (math/ceil (/ 9.0 7.0)))
;; => 2

(* 2 (- 7 2))
;; => 10

(* 3 (- 7 3))
;; => 12

(* 4 (- 7 4))
;; => 12

(* 5 (- 7 5))
;; => 10

(* 6 (- 7 6))
;; => 6

(winning-options {:time 7, :distance 9})
;; => (2 3 4 5)

(winning-options {:time 15, :distance 40})
;; => (4 5 6 7 8 9 10 11)

(winning-options {:time 30, :distance 200})
;; => (11 12 13 14 15 16 17 18 19)

(run "../input/day06/sample.txt")
;; => 288

(run "../input/day06/input.txt")
;; => 114400
