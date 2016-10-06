;; navigation.clj - The navigation bar
(ns cesena.views.partials.navigation
  (:require [ hiccup.core :refer [ html ] ]))

;; Navigation items
(def navigation-items [
  { :url "/" :title "Index" }
  "-"
  { :url "/logout" :title "Logout" }
])

(defn
  ^{
     :doc "Renders the navigation"
     :added "0.1.0"
  }
  navigation
  ;; No active page
  ([ ] (navigation nil))

  ;; With active page
  ([ current ]
    (html
      [ :div#navigation
        [ :ul
          (for [ link navigation-items ]
            ;; Render a navigation separator?
            (if (= "-" link)
              [ :li.separator ]
              [ :li.item [ :a { :href (:url link) } (:title link) ] ]))
         ] ] )))
