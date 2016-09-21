;; database.clj - database connection
(ns taobaibai.database
  (:require [ mount.core :refer [ defstate ] ]
            [ hugsql.core :refer [ def-db-fns ] ]
            [ taobaibai.config :refer [ config ] ]
            ))

(defn- create-db-spec
  "Creates the database connection spec"
  [ config ]
  (let [ path (:db config) ]
    {
     :classname "org.sqlite.JDBC"
     :subprotocol "sqlite"
     :subname path
    }))

;; The database connection state
(defstate db
  :start (create-db-spec config))

;; A macro that helps to define SQL functions
(defmacro make-queries
  "Loads queries from the given SQL file and creates
   function out of it. Refer to the Hugsql documentation
   for more details"
  [ filename ]
  `(def-db-fns ~filename))
