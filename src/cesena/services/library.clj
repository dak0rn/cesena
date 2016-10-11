;; library.clj - Library services
(ns cesena.services.library
  (:require [ cesena.database :refer [ db make-queries ] ]))

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
