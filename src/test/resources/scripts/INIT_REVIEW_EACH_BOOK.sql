insert into users(id, name, email, crated_at) values (1, 'Mehmed', 'mehmed@gmail.com', '2021-01-01 00:00:00');

insert into books(id, title, isbn, author, genre, thumbnail_url, description, publisher, pages) values (1, 'Clean Code', '9780132350884', 'Robert C. Martin', 'Software', 'https://images-na.ssl-images-amazon.com/images/I/41jEbK-jG+L._SX258_BO1', 'Software Craftsmanship', 'Prentice Hall', 464);
insert into books(id, title, isbn, author, genre, thumbnail_url, description, publisher, pages) values (2, 'The Pragmatic Programmer', '9780201616224', 'Andrew Hunt', 'Software', 'https://images-na.ssl-images-amazon.com/images/I/41asW2gU5fL._SX258_BO1', 'Your Journey to Mastery', 'Addison-Wesley Professional', 352);

insert into reviews(title, content, rating, crated_at, book_id, user_id) values ('Great Book', 'This is a great book', 5, '2021-01-01 00:00:00', 1, 1);
insert into reviews(title, content, rating, crated_at, book_id, user_id) values ('Awesome Book', 'This is an awesome book', 5, '2021-01-01 00:00:00', 2, 1);
insert into reviews(title, content, rating, crated_at, book_id, user_id) values ('Good Book', 'This is a good book', 4, '2021-01-01 00:00:00', 1, 1);
