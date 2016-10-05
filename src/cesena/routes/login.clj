;; login.clj - Login route
(ns cesena.routes.login
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.services.user :refer [ find-user ] ]
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

(defn-
  ^{
    :doc "POST handler that tries to perform a login"
    :added "1.0.0"
  }
  do-login-handler
  [ request ]
  (let [ params (:params request)
         name (:username params)
         pass (:password params)
         user (find-user name pass) ]
    (println "User " user)
    (render-login true)))



;; Exported routes
(def routes [
  (GET "/login" [ ] (login-handler))
  (POST "/login" request (do-login-handler request))
])
