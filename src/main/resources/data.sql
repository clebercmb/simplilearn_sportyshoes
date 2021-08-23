INSERT INTO categories (id, name) VALUES (1001, 'Category 1');
INSERT INTO categories (id, name) VALUES (1002, 'Category 2');
INSERT INTO categories (id, name) VALUES (1003, 'Category 3');

INSERT INTO products (id, name, category_id, price) VALUES (2001, 'Product 1', 1001, 1112.99);
INSERT INTO products (id, name, category_id, price) VALUES (2002, 'Product 2', 1001, 1999.99);
INSERT INTO products (id, name, category_id, price) VALUES (2003, 'Product 3', 1002, 889.99);
INSERT INTO products (id, name, category_id, price) VALUES (2004, 'Product 4', 1002, 778.99);
INSERT INTO products (id, name, category_id, price) VALUES (2005, 'Product 5', 1003, 541.99);
INSERT INTO products (id, name, category_id, price) VALUES (2006, 'Product 6', 1003, 322.99);

INSERT INTO users (id, name, email, password, user_type) VALUES (3001, 'Vision', 'vision@gmail.com', '123', 'ADMIN');
INSERT INTO users (id, name, email, password, user_type) VALUES (3002, 'Steave Rogers', 'steave@gmail.com', '123', 'USER');
INSERT INTO users (id, name, email, password, user_type) VALUES (3003, 'cleber', 'cleber@gmail.com', '123', 'ADMIN');