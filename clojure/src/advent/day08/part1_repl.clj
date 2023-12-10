(in-ns 'advent.day08.part1)

(load-input "../input/day08/sample1.txt")
;; => {:instructions [:right :left],
;;     :network
;;     [{:label "AAA", :left "BBB", :right "CCC"}
;;      {:label "BBB", :left "DDD", :right "EEE"}
;;      {:label "CCC", :left "ZZZ", :right "GGG"}
;;      {:label "DDD", :left "DDD", :right "DDD"}
;;      {:label "EEE", :left "EEE", :right "EEE"}
;;      {:label "GGG", :left "GGG", :right "GGG"}
;;      {:label "ZZZ", :left "ZZZ", :right "ZZZ"}]}

(index-network (:network *1))
;; => {"AAA" {:label "AAA", :left "BBB", :right "CCC"},
;;     "BBB" {:label "BBB", :left "DDD", :right "EEE"},
;;     "CCC" {:label "CCC", :left "ZZZ", :right "GGG"},
;;     "DDD" {:label "DDD", :left "DDD", :right "DDD"},
;;     "EEE" {:label "EEE", :left "EEE", :right "EEE"},
;;     "GGG" {:label "GGG", :left "GGG", :right "GGG"},
;;     "ZZZ" {:label "ZZZ", :left "ZZZ", :right "ZZZ"}}

(run "../input/day08/sample1.txt")
;; => 2

(run "../input/day08/sample2.txt")
;; => 6

(run "../input/day08/input.txt")
;; => 15989
