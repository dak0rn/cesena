;; index.clj - Views for the cesena index
(ns cesena.views.index
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]
            [ clojure.string :refer [ blank? ] ]
            [ hiccup.core :refer [ h ] ]
            ))

(defn
  ^{
     :doc "Renders the page index"
     :added "0.1.0"
  }
  render-index
  [ session books query ]
  (document "Cesena"
    (navigation session)
    [ :div.library
     [ :form { :method "GET" :action "/" }
       [ :div.search-bar
         [ :input.search { :type "text" :placeholder "Search..." :name "q" :value (h query) } ]
         (when-not (blank? query) [ :a { :href "/" } "[clear]" ])
       ]
     ]
      (for [ book books ]
        (let [ bid (:book_id book) ]
          [ :div.book
            [ :div.title [ :a { :href (str "/book/" bid) } (:title book) ] ]
            [ :div.download [ :a { :href (str "/serve/" bid) } "Download" ] ]
        ]))
    ]
    ))
