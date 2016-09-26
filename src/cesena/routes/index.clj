;; index.clj - Index route
(ns cesena.routes.index
  (:require [ compojure.core :refer [ GET ] ]
            ;; TODO views
            ))

;;; Route handler functions
(defn-
  ^{
    :doc "Handler for the application index"
    :added "1.0.0"
  }
  index-handler
  []
  "index!")

;;; Export route definitions
(def routes [
 (GET "/" [] (index-handler))
])
