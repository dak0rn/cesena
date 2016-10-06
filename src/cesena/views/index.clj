;; index.clj - Views for the cesena index
(ns cesena.views.index
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]
            ))

(defn
  ^{
     :doc "Renders the page index"
     :added "0.1.0"
  }
  render-index
  [ session ]
  (document "Cesena"
    (navigation)
    [ :pre (str session) ]))
