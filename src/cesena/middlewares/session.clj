;; session.clj - Session middleware
(ns cesena.middlewares.session
  (:require [ cesena.config :refer [ config ] ]
            [ ring.util.response :refer [ redirect ] ]))

(defn wrap-session
  ^{
    :doc "Session middleware that loads the user's session"
    :added "0.1.0"
  }
  [ handler ]
  (fn [ request ]
    (if (= "/login" (:uri request))
      ;; The login is skipped from the session handling
      (handler request)
      ;; Try to get the JWT from the request otherwise
      (if-let [ jwt (get-in request [ :cookies (get-in config [ :security :jwt :field ]) :value ])]
        (do
            (println "Found a JWT" jwt)
            (handler request))
        ;; If no JWT is present, redirect to the login
        (redirect "/login")
        ))))
