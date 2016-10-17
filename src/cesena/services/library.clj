;; library.clj - Library services
(ns cesena.services.library
  (:require [ cesena.database :refer [ db make-queries ] ]
            [ cesena.config :refer [ config ] ]
            [ clojure.java.io :refer [ file ] ]
            [ clj-time.core :refer [ now ] ]))

(make-queries "sql/library.sql")

(defn
  ^{
     :doc "Retrieves all books"
     :added "0.1.0"
  }
  all-books
  [ ]
  (query-all-books db))

(defn
  ^{
     :doc "Searches for a given term"
     :added "0.1.0"
  }
  find-books
  [ term ]
  (query-with-term db { :term (str "%" term "%") }))

(defn
  get-book
  "Returns the book identified by the given id"
  { :added "0.1.0" }
  [ book-id ]
  (let [ param { :bid book-id } ]
    (query-specific-book db param)))

(defn
  update-book
  "Updates the given book in the database by saving it.
   Only the title can be changed. The change timestamp
   will be set to the current time"
  { :added "0.1.0" }
  [ book ]
  (let [ updated-book (assoc book :date (str (now))) ]
    (query-update-book db updated-book)
    updated-book))

(defn
  folder-locked?
  "Determines if the application has write permissions in
   the library folder"
  [ ]
  (let [ source (:source config) ]
    (-> source file .canWrite)))
