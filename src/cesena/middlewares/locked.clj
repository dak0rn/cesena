;; locked.clj - Middleware that determines if the application is locked
(ns cesena.middlewares.locked
  (:require [ ring.util.response :refer [ redirect ] ]
            [ cesena.routes :refer [ url ] ]
            [ cesena.services.scanner :refer [ is-rescanning? ] ]))

(defn
  ^{
     :doc "Middleware that checks if the application is locked and redirects
           to /locked if it is"
     :added "0.1.0"
  }
  wrap-locked
  [ handler ]
  (fn [ request ]
    (if (and (not= (url "/locked") (:uri request)) (is-rescanning?))
      (redirect (url "/locked"))
      (handler request))))
