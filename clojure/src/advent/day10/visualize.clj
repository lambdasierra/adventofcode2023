(ns advent.day10.visualize
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import (java.awt Component Dimension Graphics)
           (java.awt.image BufferedImage IndexColorModel)
           (javax.imageio ImageIO)
           (javax.swing JFrame SwingUtilities)))

(set! *warn-on-reflection* true)

(defn read-image [image-path]
  (ImageIO/read (io/file image-path)))

(defn write-png [^BufferedImage image image-path]
  (ImageIO/write image
                 "PNG"
                 (io/file image-path)))

(defn display-image [^BufferedImage image
                     & {:keys [title width height]
                        :or {title "Image"}}]
  (SwingUtilities/invokeLater
   (fn []
     (let [component (proxy [Component] []
                       (paint [^Graphics graphics]
                         (.drawImage graphics image 0 0 nil))
                       (getPreferredSize []
                         (Dimension. (or width (.getWidth image nil))
                                     (or height (.getHeight image nil)))))
           frame (JFrame. (str title))]
       (.setDefaultCloseOperation frame JFrame/DISPOSE_ON_CLOSE)
       (.add frame component)
       (.pack frame)
       (.setVisible frame true)))))

(def tile-width 16)

(def tile-height 16)

(defn get-tile [^BufferedImage image x y]
  (.getSubimage image
                (* tile-width x)
                (* tile-height y)
                tile-width
                tile-height))

(defn make-tile-map [tileset-image]
  {\| (get-tile tileset-image 2 11)  ; vertical
   \- (get-tile tileset-image 1 10)  ; horizontal
   \L (get-tile tileset-image 1 12)  ; north to east
   \J (get-tile tileset-image 3 12)  ; north to west
   \7 (get-tile tileset-image 1 11)  ; south to west
   \F (get-tile tileset-image 3 11)  ; south to east
   \. (get-tile tileset-image 0 6)   ; ground
   \S (get-tile tileset-image 2 13)})  ; starting position

(defn parse-grid [string]
  (string/split-lines string))

(defn get-grid-at [grid x y]
  (nth (nth grid y) x))

(defn grid-height [grid]
  (count grid))

(defn grid-width [grid]
  (count (first grid)))

(defn draw-tiles
  "Returns a new BufferedImage generated from the tileset
  and the parsed input grid.

  Optional keyword argument :scale is an integer multiplier
  applied to the size of the tiles, default 1."
  [^BufferedImage tileset-image
   grid
   & {:keys [scale] :or {scale 1}}]
  (let [tile-map (make-tile-map tileset-image)
        buffer (BufferedImage.
                (int (* (grid-width grid) tile-width scale))
                (int (* (grid-height grid) tile-height scale))
                (.getType tileset-image)
                ^IndexColorModel (.getColorModel tileset-image))
        graphics (.createGraphics buffer)]
    (doseq [x (range (grid-width grid))
            y (range (grid-height grid))]

      (.drawImage graphics
                  (get tile-map \.)
                  (* x tile-width scale)
                  (* y tile-height scale)
                  (* tile-width scale)
                  (* tile-height scale)
                  nil)

      (let [character (get-grid-at grid x y)
            tile (get tile-map character)]
        (.drawImage graphics
                    tile
                    (* x tile-width scale)
                    (* y tile-height scale)
                    (* tile-width scale)
                    (* tile-height scale)
                    nil)))
    buffer))
