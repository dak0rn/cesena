;; core.clj - cesena core module
(ns cesena.core
  (:require
             [ mount.core :refer [ start ] ]
             [ cesena.config ]
             [ cesena.database ]
             [ cesena.services.boot :refer [ database-exists setup-database ] ]
            ))

(defn boot-application
  "Starts the application"
  [ ]
  (start
    #'cesena.config/config
    #'cesena.database/db)
  (when-not (database-exists)
    (setup-database)))

(defn -main
  "The main start function of the application"
  [ ]
  (do
    (println "Starting the application")
    (boot-application)))
