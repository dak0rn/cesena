CREATE TABLE IF NOT EXISTS cesena_user (
    user_id BLOB PRIMARY KEY,
    admin BOOLEAN,
    name TEXT NOT NULL UNIQUE,
    passwd TEXT
);
--;;
CREATE TABLE IF NOT EXISTS cesena_book (
    book_id BLOB PRIMARY KEY,
    title TEXT,
    path TEXT,
    checksum BLOB,
    date INTEGER
);
--;;
CREATE TABLE cesena (
    activity_lock INTEGER
);
--;;
INSERT INTO cesena (activity_lock) VALUES (0);
