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
  [ username password options ]
  (let [ uuid (gen-uuid)
         ;; Use nil as password if the user is locked
         passwd (if (:locked options false) nil (encrypt password))
         data { :username username :passwd passwd :userid uuid :admin (get options :admin false) } ]
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


;; Find a user for a given user Id
(defn
  ^{
     :doc "Selects the user with the given user id"
     :added "0.1.0"
  }
  find-user-by-id
  [ uid ]
  (query-user-by-id db { :uid uid }))

;; Finds all users
(defn
  ^{
     :doc "Selects all users"
     :added "0.1.0"
  }
  find-all-users
  [ ]
  (query-all-users db))

;; Lock a specific user
(defn
  ^{
     :doc "Locks the given user"
     :added "0.1.0"
  }
  lock-user
  [ user ]
  (query-lock-user db { :uid (:user_id user) }))

;; Change the password of a user
(defn
  ^{
     :doc "Changes the password of the given user"
     :added "0.1.0"
  }
  change-password
  [ user password ]
  (query-change-password db { :uid (:user_id user) :passwd (encrypt password) } ))

;; Deletes the user with the given ID if it exists
(defn
  ^{
     :doc "Deletes the given user"
     :added "0.1.0"
  }
  delete-user
  [ user ]
  (query-delete-user db { :uid (:user_id user) } ))
