CREATE TABLE IF NOT EXISTS cesena_user (
    user_id BLOB PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    passwd TEXT NOT NULL
);
--;;
CREATE TABLE IF NOT EXISTS cesena_book (
    book_id BLOB PRIMARY KEY,
    name TEXT,
    filename TEXT,
    checksum BLOB,
    date INTEGER
);
