;; session.clj - Session middleware
(ns cesena.middlewares.session
  (:require [ cesena.config :refer [ config ] ]))

(defn wrap-session
  ^{
    :doc "Session middleware that loads the user's session"
    :added "1.0.0"
  }
  [ handler ]
  (fn [ request ]
    ;; TODO Load session form the database
    (let [ cookies (:cookies request) ]
      (println "Got cookies here" cookies)
      (handler request))))
