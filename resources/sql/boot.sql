-- :name check-system-table
-- :doc "Checks if the taobaibai system table exists"
select count(name) as "exists" from sqlite_master where name = 'taobaibai';
