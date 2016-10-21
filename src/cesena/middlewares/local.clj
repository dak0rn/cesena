;; local.clj - local middleware functions
(ns cesena.middlewares.local
  (:require [ ring.util.response :refer [ redirect ] ]
            [ cesena.routes :refer [ url ] ]))

;; Middleware that ensures that the requesting user
;; is an admin
(defn
  ^{
     :doc "Requires the user in the request to be an admin"
     :added "0.1.0"
  }
  require-admin
  [ f ]
  (fn [ request ]
    (let [ user (:cesena-session request) ]
      (if (= 1 (:admin user))
        (f request)
        (redirect (url "/403"))))))
