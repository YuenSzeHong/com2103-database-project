
-- @block set db
use lms;

-- @block list books in genres
SELECT b.title, g.name FROM books b, book_genres bg, genres g
WHERE b.book_id = bg.book_id AND bg.genre_id = g.id;

-- @block list books in publisher
SELECT b.title, p.name FROM books b, book_publisher bp, publisher p
WHERE b.book_id = bp.book_id AND bp.publisher_id = p.publisher_id;

-- @block list books of author
SELECT b.title, a.name FROM books b, book_author ba, authors a
WHERE b.book_id = ba.book_id AND ba.author_id = a.id;

-- @block create view of borrow period and daily fine
CREATE VIEW borrow_period_fine AS SELECT u.user_id, r.borrow_period, r.daily_fine FROM users u, borrow_rule r
WHERE u.rule_id = r.rule_id;

-- @block list books borrowed by user with due date
SELECT b.title, br.borrow_date, br.return_date, DATE_ADD(br.borrow_date, INTERVAL bpf.borrow_period DAY) AS due_date FROM borrow_records br, books b, borrow_period_fine bpf
WHERE 
br.book_id = b.book_id AND br.borrower_id = bpf.user_id;

-- @block create view of borrowed books with due date
ALTER VIEW borrowed_books AS SELECT br.borrower_id, b.title, br.borrow_date, br.return_date, DATE_ADD(br.borrow_date, INTERVAL bpf.borrow_period DAY) AS due_date FROM borrow_records br, books b, borrow_period_fine bpf
WHERE 
br.book_id = b.book_id AND br.borrower_id = bpf.user_id;

-- @block select books are overdue
SELECT b.title, br.borrow_date, DATE_ADD(br.borrow_date, INTERVAL bpf.borrow_period DAY) AS due_date, b.returned_date FROM borrowed_books b, borrow_records br, borrow_period_fine bpf
WHERE 
b.due_date < CURDATE() AND b.borrower_id = bpf.user_id;

-- @block select user who has overdue books and their overdue fine
SELECT u.user_name, (bpf.borrow_period - DATEDIFF("2022-03-13", b.borrow_date)) AS `period remaining`, b.due_date FROM borrowed_books b, borrow_period_fine bpf, users u
WHERE 
b.due_date < CURDATE() AND b.borrower_id = bpf.user_id AND
b.borrower_id = u.user_id AND b.returned_date IS NULL
ORDER BY `period remaining` DESC;

-- @block get book info
SELECT b.title, b.isbn, g.name AS `genres`, p.name AS `publisher`, a.name AS `author`
FROM books b, book_genres bg, genres g, publisher p, book_author ba, authors a
WHERE 
    b.book_id = bg.book_id AND 
    bg.genre_id = g.id AND 
    b.publisher_id = p.publisher_id AND 
    b.book_id = ba.book_id AND 
    ba.author_id = a.id
ORDER BY b.book_id;

-- @block get books are not returned
SELECT b.title 
