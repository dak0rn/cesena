;; scanner.clj - Service responsible for populating the database
(ns cesena.services.scanner
  (:require [ mount.core :refer [ defstate ] ]
            [ buddy.core.hash :refer [ sha256 ] ]
            [ clojure.java.jdbc :refer [ with-db-transaction ] ]
            [ buddy.core.codecs :refer [ bytes->hex ] ]
            [ clj-time.core :as time ]
            [ clojure.java.io :refer [ file ] ]
            [ cesena.database :refer [ db make-queries ] ]
            [ cesena.config :refer [ config ] ])
  (:import [ java.util UUID ]))

;; Create the query functions
(make-queries "sql/scanner.sql")

;; Returns the estimated title of a book or nil
;; if it is not valid
(defn- book-title
  [ file ]
  (let [ title (re-matches #"(.*)\.pdf" (.getName file)) ]
    (when title (second title))))

;; Determines if the given File is a valid book
(defn- is-book?
  [ file ]
  (every? identity [ (.isFile file) (book-title file) ]))

;; Processes a given File
(defn
  ^{
     :doc "Processes a given file. Determines metadata and
          inserts it into the database"
     :added "0.1.0"
  }
  process-file
  ([ file ] (process-file [ db file ]))
  ([ trs file ]
  (let [ title (book-title file)
         path (.getAbsolutePath file)
         checksum (-> file (sha256) (bytes->hex))
         date (time/now)
         bid (str (UUID/randomUUID)) ]
    (query-insert-book trs { :book_id bid
                             :title title
                             :path path
                             :checksum checksum
                             :date date }))))

;; Processes a whole directory
(defn-
  ^{
     :doc "Recursively processes a given directory of files using the given processor function"
     :added "0.1.0"
  }
  process-directory
  [ directory-path processor ]
  (let [ all-files (file-seq (file directory-path))
         books (filter is-book? all-files) ]
    (loop [ book (first books) remaining (rest books) ]
      (when book
        (do (processor book))
        (recur (first remaining) (rest remaining))))))

(defn-
  ^{
     :doc "Performs the actual rescanning work"
     :added "0.1.0"
  }
  perform-rescan
  [ ]
  (query-set-lock db)
  (try
    (with-db-transaction [ trx db ]
      (let [ source (:source config)
            proc (partial process-file trx) ]
        (query-clear-db trx)
        (process-directory source proc)))
    (finally
      (query-unset-lock db))))

;; The exported service function
(defn
  ^{
     :doc "Scans the configured directory, empties the database and
           inserts all found books. The rescan is performed as a background
           task and this function returns a future for that task."
     :added "0.1.0"
  }
  rescan-library
  [ ]
  (future perform-rescan))

;; Determines if the application is currently doing a rescan
(defn
  ^{
     :doc "Determines if a rescan is in progress"
     :added "0.1.0"
  }
  is-rescanning?
  [ ]
  (-> (query-is-locked db) (:activity_lock) (= 1)))
