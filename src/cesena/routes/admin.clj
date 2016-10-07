;; admin.clj - Admin routes
(ns cesena.routes.admin
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.admin :refer [ render-admin ] ]
            [ cesena.services.user :refer [ find-all-users ] ]
            [ cesena.middlewares.local :refer [ require-admin ] ]))

;; Route handler functions
(defn-
  ^{
     :doc "Handler for /admin"
     :added "0.1.0"
  }
  handle-admin
  [ request ]
  (let [ user (:cesena-session request)
         all-users (find-all-users) ]
    (render-admin user all-users)))


;; Exported routes

(def secured-admin (require-admin handle-admin))

(def routes [
  (GET "/admin" request (secured-admin request))
])
