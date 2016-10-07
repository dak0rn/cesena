;; session.clj - Session middleware
(ns cesena.middlewares.session
  (:require [ cesena.config :refer [ config ] ]
            [ cesena.services.security :refer [ create-jwt get-claims add-jwt-cookie remove-jwt-cookie ] ]
            [ cesena.services.user :refer [ find-user-by-id ] ]
            [ ring.util.response :refer [ redirect ] ]))

(defn wrap-session
  ^{
    :doc "Session middleware that loads the user's session"
    :added "0.1.0"
  }
  [ handler ]
  (fn [ request ]
    (if (or (= "/login" (:uri request)) (= "/logout" (:uri request)))
      ;; The login is skipped from the session handling
      (handler request)
      ;; Try to get the JWT from the request otherwise
      (if-let [ jwt (get-in request [ :cookies (get-in config [ :security :jwt :field ]) :value ])]
        (try
          ;; The JWT unsigning throws if the JWT is invalid
          (let [ claim (get-claims jwt)
                 ;; If the claim unsigning did not throw, the handler can process the request
                 uid (:jti claim) ]
            (if-let [ user (find-user-by-id uid) ]
              (add-jwt-cookie (handler (assoc request :cesena-session user)) (create-jwt claim))
              (remove-jwt-cookie { :status 302 :cookies {} :body "" :headers { "Location" "/login" } })))
          (catch clojure.lang.ExceptionInfo ignore
            (println "Exception" ignore)
            (remove-jwt-cookie { :status 302 :cookies {} :body "" :headers { "Location" "/login" } })))
        ;; If no JWT is present, redirect to the login
        (redirect "/login")
        ))))
