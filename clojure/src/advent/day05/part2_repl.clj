(in-ns 'advent.day05.part2)

(with-open [rdr (io/reader "../input/day05/sample.txt")]
  (parse-input rdr))
;; => {:seeds [{:start 79, :length 14} {:start 55, :length 13}],
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

