-- :name query-all-books :? :*
-- :doc "Selects all books"
SELECT * FROM cesena_book;

-- :name query-with-term :? :*
-- :doc "Selects all books that match a given criteria"
SELECT * FROM cesena_book WHERE title LIKE :term;
