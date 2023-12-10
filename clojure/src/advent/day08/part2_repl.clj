(in-ns 'advent.day08.part2)

(run "../input/day08/sample3.txt")

(input-nodes (part1/load-input "../input/day08/sample3.txt"))
;; => ["11A" "22A"]

(input-nodes (part1/load-input "../input/day08/input.txt"))
;; => ["DFA" "BLA" "TGA" "AAA" "PQA" "CQA"]

(def the-input (part1/load-input "../input/day08/input.txt"))
(def the-instructions (:instructions the-input))
(def the-network (:network the-input))
(def the-index (part1/index-network the-network))

(count-steps the-instructions the-index "DFA")
;; => 18157

(count-steps the-instructions the-index "BLA")
;; => 14363

(count-steps the-instructions the-index "TGA")
;; => 19783

(count-steps the-instructions the-index "AAA")
;; => 15989

(count-steps the-instructions the-index "PQA")
;; => 19241

(count-steps the-instructions the-index "CQA")
;; => 12737

(gcd 1071 462)
;; => 21

(lcm 21 6)
;; => 42

(reduce lcm [18157 14363 19783 15989 19241 12737])
;; => 13830919117339

(run "../input/day08/input.txt")
;; => 13830919117339
