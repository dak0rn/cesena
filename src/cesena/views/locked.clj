;; locked.clj - View for the locked state
(ns cesena.views.locked
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]))

(defn
  ^{
     :doc "Renders the locked state page"
     :added "0.1.0"
  }
  render-locked
  [ user ]
  (document "Locked"
    (navigation user)
    [ :h1 "Locked" ]
    [ :p "The application is currently locked because a rescan is performed. Please stand by" ]
    [ :a { :href "/" } "Try again" ]))
