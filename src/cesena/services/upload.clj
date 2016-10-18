;; upload.clj - Service functions for the uploader
(ns cesena.services.upload
  (:require [ cesena.services.library :refer [ create-book ] ]))

(defn
  store-book
  "Stores an uploaded book on the server and in the database"
  { :added "0.1.0" }
  [ ]
  nil)
