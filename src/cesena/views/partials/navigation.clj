;; navigation.clj - The navigation bar
(ns cesena.views.partials.navigation
  (:require [ hiccup.core :refer [ html ] ]))

;; Navigation items
(def navigation-items [
  { :url "/" :title "Index" }
  "-"
  "admin"
  { :url "/logout" :title "Logout" }
])

(defn
  ^{
     :doc "Renders the navigation"
     :added "0.1.0"
  }
  navigation
  ;; No active page
  ([ user ] (navigation nil user))

  ;; With active page
  ([ current user ]
    (html
      [ :div#navigation
        [ :ul
          (for [ link navigation-items ]
            ;; Render a navigation separator?
            (if (= "-" link)
              [ :li.separator ]
              (if (and (= "admin" link) (= 1 (:admin user)))
                [ :li.item [ :a { :href "/admin" } "Configuration" ] ]
                [ :li.item [ :a { :href (:url link) } (:title link) ] ])))
         ]])))
