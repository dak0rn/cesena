;; core.clj - taobaibai core module
(ns taobaibai.core
  (:require
             [ mount.core :refer [ start ] ]
             [ taobaibai.config ]
             [ taobaibai.database ]
            ))

(defn boot-application
  "Starts the application"
  [ ]
  (do
    (start
      #'taobaibai.config/config
      #'taobaibai.database/db)))

(defn -main
  "The main start function of the application"
  [ ]
  (do
    (println "Starting the application")
    (boot-application)))
