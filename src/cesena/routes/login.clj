;; login.clj - Login route
(ns cesena.routes.login
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.services.user :refer [ find-user ] ]
            [ cesena.services.security :refer [ create-jwt ] ]
            [ cesena.config :refer [ config ] ]
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
    (if-not user
      ;; If no user has been found render the login with
      ;; the error message.
      (render-login true)

      ;; Otherwise the cookie is created
      (let [ uid (:user_id user)
             jwt-field (get-in [ :security :jwt :field ] config)
             jwt (create-jwt { :jti uid }) ]
        ;; Response map
        {
          ;; Redirect to the root
          :status 302 :headers { "Location" "/" }

          ;; Set the cookie
          :cookies (assoc {} jwt-field jwt)

          :body ""
       }))))



;; Exported routes
(def routes [
  (GET "/login" [ ] (login-handler))
  (POST "/login" request (do-login-handler request))
])
