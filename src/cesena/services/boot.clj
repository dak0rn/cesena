;; boot.clj - boot service
;; Creates the database and prepares the environment
(ns cesena.services.boot
  (:require [ cesena.database :refer [ make-queries db ] ]
            [ clojure.java.io :refer [ resource ] ]
            [ clojure.string :refer [ split ] ]
            [ clojure.java.jdbc :refer [ db-do-commands ] ]))

;; Load queries for this service
(make-queries "sql/boot.sql")

(defn database-exists
  "Determines if the database has already been setup"
  { :added "0.1.0" }
  [ ]
  (let [ res (check-system-table db) ]
    (= 1 (:count res))))


(defn setup-database
  "Creates the database by setting up the schema and
  populating it with an example user"
  { :added "0.1.0" }
  [ ]
  (let [ sql (-> "sql/setup.sql" resource slurp (split #"--;;")) ]
    (doseq [ statement sql ] (db-do-commands db statement))))
