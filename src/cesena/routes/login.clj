;; login.clj - Login route
(ns cesena.routes.login
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.services.user :refer [ find-user ] ]
            [ cesena.services.security :refer [ create-jwt add-jwt-cookie ] ]
            [ cesena.config :refer [ config ] ]
            [ cesena.views.login :refer [ render-login ] ]))

;;; Route handler functions
(defn-
  ^{
    :doc "Handler for /login"
    :added "0.1.0"
  }
  login-handler
  [ ]
  (render-login))

(defn-
  ^{
    :doc "POST handler that tries to perform a login"
    :added "0.1.0"
  }
  do-login-handler
  [ request ]
  (let [ params (:params request)
         name (:username params)
         pass (:password params)
         user (find-user name pass) ]
    (if-not user
      ;; If no user has been found render the login with
      ;; the error message.
      (render-login true)

      ;; Otherwise the cookie is created
      (let [ uid (:user_id user)
             jwt-field (get-in config [ :security :jwt :field ])
             jwt (create-jwt { :jti uid }) ]
        ;; Response map
        (add-jwt-cookie { :status 302 :headers { "Location" "/" } :cookies {} :body "" } jwt)
       ))))



;; Exported routes
(def routes [
  (GET "/login" [ ] (login-handler))
  (POST "/login" request (do-login-handler request))
])
