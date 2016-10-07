-- :name query-create-user :i!
-- :doc "Creates a new user account in the database"
INSERT INTO cesena_user (user_id, name, passwd, admin) VALUES (:userid, :username, :passwd, :admin);

-- :name query-user-by-name :? :1
-- :doc "Finds all users for the given username"
SELECT * FROM cesena_user WHERE name = :name limit 1;

-- :name query-user-by-id :? :1
-- :doc "Finds the user with the given id"
SELECT * FROM cesena_user WHERE user_id = :uid AND passwd IS NOT NULL LIMIT 1;

-- :name query-all-users :? :*
-- :doc "Finds all users"
SELECT * FROM cesena_user;

-- :name query-lock-user :! :n
-- :doc "Locks the user with the given id"
UPDATE cesena_user SET passwd = NULL WHERE user_id = :uid;

-- :name query-change-password :! :n
-- :doc "Changes the user's password"
UPDATE cesena_user SET passwd = :passwd WHERE user_id = :uid;
