;; user.clj - Services for the user management
(ns cesena.services.user
  (:require [ cesena.database :refer [ make-queries db ] ]
            [ cesena.services.security :refer [ verify encrypt ] ]))

;; Load user-related queries
(make-queries "sql/user.sql")

(defn- gen-uuid
  [ ]
  (str (java.util.UUID/randomUUID)))

;; Create a new user
(defn
  ^{
     :doc "Creates a new user using the given username and password.
          Automatically generates a new unique user ID"
     :added "0.1.0"
  }
  create-user
  [ username password ]
  (let [ uuid (gen-uuid)
         passwd (encrypt password)
         data { :username username :passwd passwd :userid uuid } ]
    (query-create-user db data)))

;; Find a user for the given username and password
(defn
  ^{
     :doc "Selects the user for the given username and
          password. Returns nil if no user could be found"
     :added "0.1.0"
  }
  find-user
  [ username password ]
  (when-let [ user (query-user-by-name db { :name username }) ]
    (let [ pw (:passwd user) ]
      (when (verify password pw) user))))

