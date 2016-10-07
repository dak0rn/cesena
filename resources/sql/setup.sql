CREATE TABLE IF NOT EXISTS cesena_user (
    user_id BLOB PRIMARY KEY,
    admin BOOLEAN,
    name TEXT NOT NULL UNIQUE,
    passwd TEXT
);
--;;
CREATE TABLE IF NOT EXISTS cesena_book (
    book_id BLOB PRIMARY KEY,
    name TEXT,
    filename TEXT,
    checksum BLOB,
    date INTEGER
);
--;;
CREATE TABLE cesena (version TEXT);
