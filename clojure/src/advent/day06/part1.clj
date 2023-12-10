(ns advent.day06.part1
  (:require [clojure.java.io :as io]
            [clojure.math :as math]
            [clojure.string :as string]))

(defn load-input [input-path]
  (with-open [rdr (io/reader input-path)]
    (let [[times-line distance-line] (line-seq rdr)]
      (mapv (fn [time-string distance-string]
              {:time (parse-long time-string)
               :distance (parse-long distance-string)})
            (subvec (string/split times-line #"\s+") 1)
            (subvec (string/split distance-line #"\s+") 1)))))

(defn winning-options [{:keys [time distance]}]
  (let [min-speed (math/round (math/ceil (/ distance time)))]
    (->> (range min-speed time)
         (filter (fn [hold] (< distance (* hold (- time hold))))))))

(defn run [input-path]
  (transduce (comp (map winning-options)
                   (map count))
             *
             1
             (load-input input-path)))
