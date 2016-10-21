;; navigation.clj - The navigation bar
(ns cesena.views.partials.navigation
  (:require [ hiccup.core :refer [ html ] ]
            [ hiccup.element :refer [ link-to ] ]))

;; Navigation items
(def navigation-items [
  { :url "/" :title "Index" }
  { :url "/upload" :title "Upload" }
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
              [ :li.item
                (if (and (= "admin" link) (= 1 (:admin user)))
                 (link-to "/admin" "Configuration")
                 (link-to (:url link) (:title link)))
               ]))
         ]])))
