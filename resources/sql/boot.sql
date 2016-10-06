-- :name check-system-table :? :1
-- :doc "Checks if the cesena system table exists"
select count(name) as "exists" from sqlite_master where name = 'cesena';
