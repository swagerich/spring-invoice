INSERT INTO clients (name,lastname,mail,creation_date,foto) VALUES ('ERICH','hc','erich@gmail.com','2022-11-23','');
INSERT INTO clients (name,lastname,mail,creation_date,foto) VALUES ('EVELING','hc','eveling@gmail.com','2022-11-23','');


INSERT INTO products(name,price,creation_date) VALUES ('LAPTOP LEGION 5I PRO',5500,NOW());
INSERT INTO products(name,price,creation_date) VALUES ('LAPTOP HP ',4000,NOW());
INSERT INTO products(name,price,creation_date) VALUES ('LAPTOP ASUS ',5000,NOW());
INSERT INTO products(name,price,creation_date) VALUES ('LAPTOP MSI ',7000,NOW());
INSERT INTO products(name,price,creation_date) VALUES ('LAPTOP APPLE UTLIMATE',8000,NOW());


INSERT INTO invoices(description,observation,creation_date,client_id) VALUES ('FACTURA LAPTOP GAMER',null,NOW(),1);

INSERT INTO invoices_details(quantity,invoice_id,product_id) VALUES (1,1,1);
INSERT INTO invoices_details(quantity,invoice_id,product_id) VALUES (2,1,2);
INSERT INTO invoices_details(quantity,invoice_id,product_id) VALUES (1,1,3);
INSERT INTO invoices_details(quantity,invoice_id,product_id) VALUES (1,1,4);

INSERT INTO invoices(description,observation,creation_date,client_id) VALUES ('FACTURA EQUIPOS GAMER',null,NOW(),1);

INSERT INTO invoices_details(quantity,invoice_id,product_id) VALUES (3,2,5);

INSERT INTO users(username,password,enabled) VALUES ('erich','$2a$10$k29ZC8YToUVyxAXlklw3XuSPRYijegyf0RAu/ipTjrOB5iYjaENgO',true);
INSERT INTO users(username,password,enabled) VALUES ('jose','$2a$10$k29ZC8YToUVyxAXlklw3XuSPRYijegyf0RAu/ipTjrOB5iYjaENgO',true);

INSERT INTO roles(user_id,authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO roles(user_id,authority) VALUES (2,'ROLE_USER');