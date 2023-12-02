(ns advent.day02.part2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def sample
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

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

(defn min-cubes [{:keys [samples]}]
  (reduce (fn [minimums sample]
            (reduce-kv (fn [minimums color cube-count]
                         (if (< (get minimums color) cube-count)
                           (assoc minimums color cube-count)
                           minimums))
                       minimums
                       sample))
          {"red" 0, "green" 0, "blue" 0}
          samples))

(defn power [cube-counts]
  (reduce * (vals cube-counts)))

(comment
  (map parse-line (line-seq (java.io.BufferedReader.
                             (java.io.StringReader. sample))))

  (map min-cubes *1)
  ;; => ({"red" 4, "green" 2, "blue" 6}
  ;;     {"red" 1, "green" 3, "blue" 4}
  ;;     {"red" 20, "green" 13, "blue" 6}
  ;;     {"red" 14, "green" 3, "blue" 15}
  ;;     {"red" 6, "green" 3, "blue" 2})

  (map power *1)
  ;; => (48 12 1560 630 36)

  (reduce + *1)
  ;; => 2286
  
  )

(defn run [input-path]
  (with-open [rdr (io/reader input-path)]
    (transduce (comp (map parse-line)
                     (map min-cubes)
                     (map power))
               +
               0
               (line-seq rdr))))

(comment
  (run "../input/day02/input.txt")
  ;; => 86036

)
