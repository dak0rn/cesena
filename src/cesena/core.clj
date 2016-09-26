;; core.clj - cesena core module
(ns cesena.core
  (:require
             [ mount.core :refer [ start ] ]
             [ cesena.config ]
             [ cesena.database ]
            ))

(defn boot-application
  "Starts the application"
  [ ]
  (do
    (start
      #'cesena.config/config
      #'cesena.database/db)))

(defn -main
  "The main start function of the application"
  [ ]
  (do
    (println "Starting the application")
    (boot-application)))
