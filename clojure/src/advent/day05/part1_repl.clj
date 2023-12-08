(in-ns 'advent.day05.part1)

(parse-seeds "seeds: 1972667147 405592018 1450194064")
;; => {:seeds [1972667147 405592018 1450194064]}

(parse-mapping (string/split-lines
                "soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15"))
;; => {:source "soil",
;;     :target "fertilizer",
;;     :ranges
;;     [{:target-start 0, :source-start 15, :length 37}
;;      {:target-start 37, :source-start 52, :length 2}
;;      {:target-start 39, :source-start 0, :length 15}]}

(split-on-blank-lines
 (string/split-lines "seeds: 1 2 3

thing-to-thing map:
1 2 3
4 5 6

another-thing-to-thing map:
7 8 9
10 11 12"))
;; => (("seeds: 1 2 3")
;;     ("thing-to-thing map:" "1 2 3" "4 5 6")
;;     ("another-thing-to-thing map:" "7 8 9" "10 11 12"))

(with-open [rdr (io/reader "../input/day05/sample.txt")]
  (parse-input rdr))
;; => {:seeds [79 14 55 13],
;;     :mappings
;;     [{:source "seed",
;;       :target "soil",
;;       :ranges
;;       [{:target-start 50, :source-start 98, :length 2}
;;        {:target-start 52, :source-start 50, :length 48}]}
;;      {:source "soil",
;;       :target "fertilizer",
;;       :ranges
;;       [{:target-start 0, :source-start 15, :length 37}
;;        {:target-start 37, :source-start 52, :length 2}
;;        {:target-start 39, :source-start 0, :length 15}]}
;;      {:source "fertilizer",
;;       :target "water",
;;       :ranges
;;       [{:target-start 49, :source-start 53, :length 8}
;;        {:target-start 0, :source-start 11, :length 42}
;;        {:target-start 42, :source-start 0, :length 7}
;;        {:target-start 57, :source-start 7, :length 4}]}
;;      {:source "water",
;;       :target "light",
;;       :ranges
;;       [{:target-start 88, :source-start 18, :length 7}
;;        {:target-start 18, :source-start 25, :length 70}]}
;;      {:source "light",
;;       :target "temperature",
;;       :ranges
;;       [{:target-start 45, :source-start 77, :length 23}
;;        {:target-start 81, :source-start 45, :length 19}
;;        {:target-start 68, :source-start 64, :length 13}]}
;;      {:source "temperature",
;;       :target "humidity",
;;       :ranges
;;       [{:target-start 0, :source-start 69, :length 1}
;;        {:target-start 1, :source-start 0, :length 69}]}
;;      {:source "humidity",
;;       :target "location",
;;       :ranges
;;       [{:target-start 60, :source-start 56, :length 37}
;;        {:target-start 56, :source-start 93, :length 4}]}]}

(with-open [in (io/reader "../input/day05/input.txt")
            out (io/writer "/tmp/output.edn")]
  (binding [*out* (java.io.PrintWriter. out)]
    (clojure.pprint/pprint (parse-input in))))
;; => nil

mapping-order
;; => ({:source "seed", :target "soil"}
;;     {:source "soil", :target "fertilizer"}
;;     {:source "fertilizer", :target "water"}
;;     {:source "water", :target "light"}
;;     {:source "light", :target "temperature"}
;;     {:source "temperature", :target "humidity"}
;;     {:source "humidity", :target "location"})

(def seed-to-soil-ranges
  [{:target-start 50, :source-start 98, :length 2}
   {:target-start 52, :source-start 50, :length 48}])

(convert 79 seed-to-soil-ranges)
;; => 81
(convert 14 seed-to-soil-ranges)
;; => 14
(convert 55 seed-to-soil-ranges)
;; => 57
(convert 13 seed-to-soil-ranges)
;; => 13

(def sample-data
  (with-open [rdr (io/reader "../input/day05/sample.txt")]
    (parse-input rdr)))

(defn seed-to-location-path [seed mappings]
  (reductions (fn [source-value {:keys [ranges]}]
                (convert source-value ranges))
              seed
              mappings))

(seed-to-location-path 79 (:mappings sample-data))
;; => (79 81 81 81 74 78 78 82)
(seed-to-location-path 14 (:mappings sample-data))
;; => (14 14 53 49 42 42 43 43)
(seed-to-location-path 55 (:mappings sample-data))
;; => (55 57 57 53 46 82 82 86)
(seed-to-location-path 13 (:mappings sample-data))
;; => (13 13 52 41 34 34 35 35)

(run "../input/day05/sample.txt")
;; => 35

(run "../input/day05/input.txt")
;; => 662197086
