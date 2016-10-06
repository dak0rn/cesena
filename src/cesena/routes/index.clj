;; index.clj - Index route
(ns cesena.routes.index
  (:require [ compojure.core :refer [ GET ] ]
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
  (render-index (:cesena-session request)))

;;; Export route definitions
(def routes [
  (GET "/" request (index-handler request))
])
