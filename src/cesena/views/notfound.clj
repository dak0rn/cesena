;; notfound.clj - 404 page
(ns cesena.views.notfound
  (:require [ cesena.views.partials.document :refer [ document ] ]))


(defn
  render-404
  "Renders the 404 page"
  { :added "0.1.0" }
  [ ]
  (document  "404"
    [ :h1 [ :a { :href "/" } "&larr;" ] "&nbsp;Not found" ]))
