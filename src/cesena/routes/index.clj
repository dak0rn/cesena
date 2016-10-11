;; index.clj - Index route
(ns cesena.routes.index
  (:require [ compojure.core :refer [ GET ] ]
            [ cesena.services.library :refer [ all-books find-books ] ]
            [ clojure.string :refer [ blank? ] ]
            [ cesena.views.index :refer [ render-index ] ]
            ))

;;; Route handler functions
(defn-
  ^{
    :doc "Handler for the application index"
    :added "1.0.0"
  }
  index-handler
  [ request ]
  (let [ term (get-in request [ :query-params "q" ])
         session (:cesena-session request ) ]
    (if-not (blank? term)
      (render-index session (find-books term) term)
      (render-index session (all-books) ""))))

;;; Export route definitions
(def routes [
  (GET "/" request (index-handler request))
])
