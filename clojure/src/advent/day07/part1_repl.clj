(in-ns 'advent.day07.part1)

card-value
;; => {\A 12,
;;     \J 9,
;;     \K 11,
;;     \Q 10,
;;     \2 0,
;;     \3 1,
;;     \4 2,
;;     \T 8,
;;     \5 3,
;;     \6 4,
;;     \7 5,
;;     \8 6,
;;     \9 7}

(hand-type "AAAAA")
;; => :five-of-a-kind

(hand-type "AA8AA")
;; => :four-of-a-kind

(hand-type "23332")
;; => :full-house

(hand-type "TTT98")
;; => :three-of-a-kind

(hand-type "23432")
;; => :two-pair

(hand-type "A23A4")
;; => :one-pair

(hand-type "23456")
;; => :high-card

hand-type-value
;; => {:five-of-a-kind 6,
;;     :four-of-a-kind 5,
;;     :full-house 4,
;;     :three-of-a-kind 3,
;;     :two-pair 2,
;;     :one-pair 1,
;;     :high-card 0}

(sort compare-hands ["33332" "2AAAA"])
;; => ("2AAAA" "33332")

(sort compare-hands ["77888" "77788"])
;; => ("77788" "77888")

(load-input "../input/day07/sample.txt")
;; => [{:hand "32T3K", :bid 765}
;;     {:hand "T55J5", :bid 684}
;;     {:hand "KK677", :bid 28}
;;     {:hand "KTJJT", :bid 220}
;;     {:hand "QQQJA", :bid 483}]

(run "../input/day07/sample.txt")
;; => 6440

(run "../input/day07/input.txt")
;; => 251058093
