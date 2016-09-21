;; boot.clj - boot service
;; Creates the database and prepares the environment
(ns taobaibai.services.boot
  (:require [ taobaibai.database :refer [ make-queries db ] ]))

;; Load queries for this service
(make-queries "sql/boot.sql")

(defn database-exists
  "Checks if the required tables in the database exists"
  [ ]
  (let [ res (check-system-table db) ]
    (= 1 (:count res))))
