;; logout.clj - Logout route that removes the user's session
(ns cesena.routes.logout
  (:require [ compojure.core :refer [ GET ] ]
            [ cesena.services.security :refer [ remove-jwt-cookie ] ]))

;;; Route handlers
(defn-
  ^{
     :doc "Handler for /logout"
     :added "0.1.0"
  }
  logout-handler
  [ ]
  (remove-jwt-cookie { :status 302 :headers { "Location" "/login" } :cookies {} }))

;;; Routes
(def routes [
  (GET "/logout" [ ] (logout-handler))
])
