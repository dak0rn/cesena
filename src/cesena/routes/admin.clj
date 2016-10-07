;; admin.clj - Admin routes
(ns cesena.routes.admin
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.admin :refer [ render-admin ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.services.user :refer [ find-all-users create-user ] ]
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
         query (:query-params request)
         all-users (find-all-users) ]
    (render-admin user all-users query)))

(defn-
  ^{
     :doc "Handler for creating a new user"
     :added "0.1.0"
  }
  handle-create-user
  [ request ]
  (let [ user-def (:form-params request)
         name (get user-def "name")
         passwd (get user-def "passwd")
         is-admin (= "on" (get user-def "admin")) ]
    (if (or (empty? name) (empty? passwd))
      (redirect "/admin?error=values-missing")
      (do
        (create-user name passwd { :admin is-admin })
        (redirect "/admin?success=created")))))

;; Exported routes

(def secured-admin (require-admin handle-admin))
(def secured-create (require-admin handle-create-user))

(def routes [
  (GET "/admin" request (secured-admin request))
  (POST "/admin/create" request (secured-create request))
])
