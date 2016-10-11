;; locked.clj - Routes for the locked state
(ns cesena.routes.locked
  (:require [ compojure.core :refer [ GET ] ]
            [ cesena.views.locked :refer [ render-locked ] ]))

;; Route handlers
(defn-
  ^{
     :doc "Handler for /locked"
     :added "0.1.0"
  }
  handle-locked
  [ request ]
  (render-locked (:cesena-session request)))

(def routes [
  (GET "/locked" request (handle-locked request))
])
