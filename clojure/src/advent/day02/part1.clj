(ns advent.day02.part1
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def sample
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(def maximums
  {"red" 12
   "green" 13
   "blue" 14})

(defn parse-cube-sample [sample]
  (let [parts (string/split sample #", ")]
    (reduce (fn [result part]
                 (let [[_ number color] (re-matches #"^(\d+) (red|blue|green)" part)]
                   (assoc result color (parse-long number))))
            {}
            parts)))

(defn parse-line [line]
  (let [[_ game-id game-summary] (re-matches #"^Game (\d+): (.+)$" line)
        cube-samples (string/split game-summary #"; ")]
    {:game-id (parse-long game-id)
     :samples (map parse-cube-sample cube-samples)}))

(defn possible? [{:keys [samples]}]
  (every? (fn [sample]
            (every? (fn [color]
                      (<= (get sample color 0)
                          (get maximums color)))
                    (keys maximums)))
          samples))

(comment
  (map parse-line (line-seq (java.io.BufferedReader.
                             (java.io.StringReader. sample))))
  ;; => ({:game-id 1,
  ;;      :samples
  ;;      ({"blue" 3, "red" 4} {"red" 1, "green" 2, "blue" 6} {"green" 2})}
  ;;     {:game-id 2,
  ;;      :samples
  ;;      ({"blue" 1, "green" 2}
  ;;       {"green" 3, "blue" 4, "red" 1}
  ;;       {"green" 1, "blue" 1})}
  ;;     {:game-id 3,
  ;;      :samples
  ;;      ({"green" 8, "blue" 6, "red" 20}
  ;;       {"blue" 5, "red" 4, "green" 13}
  ;;       {"green" 5, "red" 1})}
  ;;     {:game-id 4,
  ;;      :samples
  ;;      ({"green" 1, "red" 3, "blue" 6}
  ;;       {"green" 3, "red" 6}
  ;;       {"green" 3, "blue" 15, "red" 14})}
  ;;     {:game-id 5,
  ;;      :samples
  ;;      ({"red" 6, "blue" 1, "green" 3} {"blue" 2, "red" 1, "green" 2})})

  (map :game-id (filter possible? *1))
  ;; => (1 2 5)
  )

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (transduce (comp (map parse-line)
                     (filter possible?)
                     (map :game-id))
               +
               0
               (line-seq rdr))))

(comment
  (run "../input/day02/input.txt")
  ;; => 2600
)
