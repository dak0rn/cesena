;; admin.clj - Admin routes
(ns cesena.routes.admin
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.admin :refer [ render-admin ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.services.user :refer [ find-all-users create-user lock-user change-password ] ]
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

(defn-
  ^{
     :doc "Handler for /admin/lock that locks a given user"
     :added "0.1.0"
  }
  handle-lock
  [ request ]
  (if-let [ who (get-in request [ :form-params "uid" ]) ]
    (do
      (lock-user { :user_id who }) ;; Emulating a user here
      (redirect "/admin?success=locked"))
    (redirect "/admin?error=lock-missing")))


(defn-
  ^{
     :doc "Handler for /admin/change that changes the user's password"
     :added "0.1.0"
  }
  handle-change
  [ request ]
  (let [ form-params (:form-params request)
         who (get form-params "uid")
         password (get form-params "password") ]
    (if (or (empty? who) (empty? password))
       (redirect "/admin?error=change-missing")
       (do
         (change-password { :user_id who } password)
         (redirect "/admin?success=change")))))

;; Exported routes

(def secured-admin (require-admin handle-admin))
(def secured-create (require-admin handle-create-user))
(def secured-lock (require-admin handle-lock))
(def secured-change (require-admin handle-change))

(def routes [
  (GET "/admin" request (secured-admin request))
  (POST "/admin/create" request (secured-create request))
  (POST "/admin/lock" request (secured-lock request))
  (POST "/admin/change" request (secured-change request))
])
