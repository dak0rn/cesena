;; config.clj - Configuration file loader and parser
(ns taobaibai.config
  (:require
    [ clojure.edn :as edn ]
    [ clojure.java.io :refer [ resource file ] ]
    [ mount.core :refer [ defstate ] ]
    ))

;; Different configuration file locations
(def config-files
  (map file [
             "/opt/taobaibai.edn"
             "/etc/taobaibai.edn"
             ]))

(defn- get-config-contents
  [ files ]
  (if-let [ config-file (first (filter #(.exists %) files)) ]
    (slurp config-file)
    (-> "taobaibai.edn"
        resource
        slurp)))

(defn- load-configuration
  "Loads the configuration file"
  []
  (let [ contents (get-config-contents config-files) ]
    (edn/read-string contents)))


(defstate config
  :start (load-configuration))
