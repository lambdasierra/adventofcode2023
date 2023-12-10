(in-ns 'advent.day07.part2)

(hand-type "32T3K")
;; => :one-pair

(hand-type "KK677")
;; => :two-pair

(hand-type "T55J5")
;; => :four-of-a-kind

(hand-type "KTJJT")
;; => :four-of-a-kind

(hand-type "QQQJA")
;; => :four-of-a-kind

(run "../input/day07/sample.txt")
;; => 5905

(run "../input/day07/input.txt")
;; => 249781879
