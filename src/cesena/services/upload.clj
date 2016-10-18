;; upload.clj - Service functions for the uploader
(ns cesena.services.upload
  (:require [ cesena.services.library :refer [ create-book ] ]
            [ cesena.config :refer [ config ] ]
            [ buddy.core.hash :refer [ sha256 ] ]
            [ buddy.core.codecs :refer [ bytes->hex ] ]
            [ clojure.java.io :refer [ delete-file copy file ] ])
  (:import [ java.util UUID ] ))

(defn
  store-book
  "Stores an uploaded book on the server and in the database"
  { :added "0.1.0" }
  [ title filename tempfile ]
  ;; The file is moved first. If that succeeds and the database
  ;; insert fails, the library can still be created by rescanning
  ;; the library folder
  (let [ uuid (UUID/randomUUID)
         path (str (:source config) "/" uuid ".pdf")
         outfile (file path)
         infile (file tempfile)
         checksum (-> infile sha256 bytes->hex)
         book { :title title :checksum checksum :path path } ]
    (do
      (copy infile outfile)
      (delete-file infile))

    ;; Insert the new book into the database
    (create-book book)))
