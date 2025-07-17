-- Tạo database cho Book Service
CREATE DATABASE IF NOT EXISTS bookservice_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Sử dụng database
USE bookservice_db;

-- Tạo bảng books (Spring JPA sẽ tự động tạo nhưng đây là backup)
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    download_count INT DEFAULT 0,
    birth_year INT,
    death_year INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title(100)),
    INDEX idx_download_count (download_count)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng authors
CREATE TABLE IF NOT EXISTS book_authors (
    book_id BIGINT,
    author VARCHAR(255),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    INDEX idx_author (author)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng languages
CREATE TABLE IF NOT EXISTS book_languages (
    book_id BIGINT,
    language VARCHAR(10),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    INDEX idx_language (language)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng subjects
CREATE TABLE IF NOT EXISTS book_subjects (
    book_id BIGINT,
    subject VARCHAR(255),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    INDEX idx_subject (subject)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng bookshelves
CREATE TABLE IF NOT EXISTS book_bookshelves (
    book_id BIGINT,
    bookshelf VARCHAR(255),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    INDEX idx_bookshelf (bookshelf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng formats
CREATE TABLE IF NOT EXISTS book_formats (
    book_id BIGINT,
    format_key VARCHAR(100),
    format_value TEXT,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    INDEX idx_format_key (format_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Thêm một số dữ liệu mẫu
INSERT IGNORE INTO books (id, title, download_count, birth_year, death_year) VALUES 
(1, 'Pride and Prejudice', 50000, 1775, 1817),
(2, 'Alice''s Adventures in Wonderland', 45000, 1832, 1898),
(3, 'Frankenstein; Or, The Modern Prometheus', 40000, 1797, 1851);

INSERT IGNORE INTO book_authors (book_id, author) VALUES 
(1, 'Jane Austen'),
(2, 'Lewis Carroll'),
(3, 'Mary Wollstonecraft Shelley');

INSERT IGNORE INTO book_languages (book_id, language) VALUES 
(1, 'en'),
(2, 'en'),
(3, 'en');

INSERT IGNORE INTO book_subjects (book_id, subject) VALUES 
(1, 'England -- Social life and customs -- 19th century -- Fiction'),
(1, 'Love stories'),
(2, 'Fantasy fiction'),
(2, 'Children''s stories'),
(3, 'Science fiction'),
(3, 'Gothic fiction');

-- Hiển thị thông tin
SELECT 'Database bookservice_db created successfully!' as message;
SELECT COUNT(*) as total_books FROM books;
