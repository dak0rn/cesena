-- :name query-create-user :i!
-- :doc "Creates a new user account in the database"
INSERT INTO cesena_user (user_id, name, passwd) VALUES (:userid, :username, :passwd);

-- :name query-user-by-name :? :1
-- :doc "Finds all users for the given username"
SELECT * FROM cesena_user WHERE name = :name limit 1;
