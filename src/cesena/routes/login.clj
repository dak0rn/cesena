;; login.clj - Login route
(ns cesena.routes.login
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.login :refer [ render-login ] ]))

;;; Route handler functions
(defn-
  ^{
    :doc "Handler for /login"
    :added "1.0.0"
  }
  login-handler
  [ ]
  (render-login))

;; Exported routes
(def routes [
  (GET "/login" [ ] (login-handler))
])
