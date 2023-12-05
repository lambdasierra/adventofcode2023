(ns advent.day03.part2
  (:require [clojure.java.io :as io]
            [advent.day03.part1 :as part1]))

(comment
  (into []
        (filter (fn [{:keys [character]}]
                  (= \* character)))
        (part1/scan-input "../input/day03/sample.txt"))
  ;; => [{:x 3, :y 1, :character \*}
  ;;     {:x 3, :y 4, :character \*}
  ;;     {:x 5, :y 8, :character \*}]

  (into []
        (part1/find-numbers)
        (part1/scan-input "../input/day03/sample.txt"))
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

(defn index-numbers [numbers]
  (reduce
   (fn [result {:keys [positions] :as match}]
     (reduce (fn [m k] (assoc m k match))
             result
             positions))
   {}
   numbers))

(comment
  (index-numbers
   (eduction
    (part1/find-numbers)
    (part1/scan-input "../input/day03/sample.txt")))
  ;; => {[8 7] {:number 755, :positions [[6 7] [7 7] [8 7]]},
  ;;     [2 2] {:number 35, :positions [[2 2] [3 2]]},
  ;;     [0 0] {:number 467, :positions [[0 0] [1 0] [2 0]]},
  ;;     [3 9] {:number 664, :positions [[1 9] [2 9] [3 9]]},
  ;;     [7 7] {:number 755, :positions [[6 7] [7 7] [8 7]]},
  ;;     [1 0] {:number 467, :positions [[0 0] [1 0] [2 0]]},
  ;;     [7 2] {:number 633, :positions [[6 2] [7 2] [8 2]]},
  ;;     [6 7] {:number 755, :positions [[6 7] [7 7] [8 7]]},
  ;;     [1 9] {:number 664, :positions [[1 9] [2 9] [3 9]]},
  ;;     [2 9] {:number 664, :positions [[1 9] [2 9] [3 9]]},
  ;;     [4 6] {:number 592, :positions [[2 6] [3 6] [4 6]]},
  ;;     [1 4] {:number 617, :positions [[0 4] [1 4] [2 4]]},
  ;;     [8 2] {:number 633, :positions [[6 2] [7 2] [8 2]]},
  ;;     [8 5] {:number 58, :positions [[7 5] [8 5]]},
  ;;     [7 9] {:number 598, :positions [[5 9] [6 9] [7 9]]},
  ;;     [5 9] {:number 598, :positions [[5 9] [6 9] [7 9]]},
  ;;     [2 4] {:number 617, :positions [[0 4] [1 4] [2 4]]},
  ;;     [3 6] {:number 592, :positions [[2 6] [3 6] [4 6]]},
  ;;     [7 0] {:number 114, :positions [[5 0] [6 0] [7 0]]},
  ;;     [6 9] {:number 598, :positions [[5 9] [6 9] [7 9]]},
  ;;     [2 0] {:number 467, :positions [[0 0] [1 0] [2 0]]},
  ;;     [0 4] {:number 617, :positions [[0 4] [1 4] [2 4]]},
  ;;     [7 5] {:number 58, :positions [[7 5] [8 5]]},
  ;;     [2 6] {:number 592, :positions [[2 6] [3 6] [4 6]]},
  ;;     [5 0] {:number 114, :positions [[5 0] [6 0] [7 0]]},
  ;;     [6 2] {:number 633, :positions [[6 2] [7 2] [8 2]]},
  ;;     [6 0] {:number 114, :positions [[5 0] [6 0] [7 0]]},
  ;;     [3 2] {:number 35, :positions [[2 2] [3 2]]}}
)

(defn find-adjacent-numbers [number-index found-symbol]
  (let [{:keys [x y]} found-symbol]
    (into #{}
          (comp (map (fn [offset]
                       (part1/point-add [x y] offset)))
                (keep #(get number-index %)))
          part1/adjacency-offsets)))

(comment
  (let [input (part1/scan-input "../input/day03/sample.txt")
        number-index (index-numbers (eduction (part1/find-numbers) input))]
    (into []
          (comp
           (filter (fn [{:keys [character]}] (= \* character)))
           (map #(find-adjacent-numbers number-index %)))
          input))
  ;; => [#{{:number 35, :positions [[2 2] [3 2]]}
  ;;       {:number 467, :positions [[0 0] [1 0] [2 0]]}}
  ;;     #{{:number 617, :positions [[0 4] [1 4] [2 4]]}}
  ;;     #{{:number 755, :positions [[6 7] [7 7] [8 7]]}
  ;;       {:number 598, :positions [[5 9] [6 9] [7 9]]}}]

  )

(defn find-gears [input]
  (let [number-index (index-numbers (eduction (part1/find-numbers) input))]
    (eduction
     (filter (fn [{:keys [character]}] (= \* character)))
     (map #(find-adjacent-numbers number-index %))
     (filter #(= 2 (count %)))

     input)))

(defn run [input-path]
    (transduce (map (fn [gear]
                    (let [numbers (seq gear)]
                      (* (:number (first numbers))
                         (:number (second numbers))))))
             +
             0
             (find-gears (part1/scan-input input-path))))

(comment
  (run "../input/day03/sample.txt")
  ;; => 467835

  (run "../input/day03/input.txt")
  ;; => 86879020

  )
