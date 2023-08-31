-- Cargamos información en la tabla usuarios.
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES (1, 'edgarvivar', '1234567890', true, 'Edgar', 'Vivar', 'edgarvivar@gmail.com', CURDATE());
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES (2, 'liomessi', '1234567890', true, 'Lionel', 'Messi', 'liomessi@gmail.com', CURDATE());
INSERT INTO usuarios (nombre, apellido, email, fecha_creacion, username, password, enabled) values ('Cristiano', 'Ronaldo', 'cristiano_cr07@gmail.com', curdate(), 'cristianoronaldocr07', '1234567890', true);
INSERT INTO usuarios (nombre, apellido, email, fecha_creacion, username, password, enabled) values ('nombre_test', 'apellido_test', 'email_test@gmail.com', curdate(), 'disableduser', '1234567890', false);

INSERT INTO usuarios (nombre, apellido, email, fecha_creacion, username, password, enabled) values ('Neymar', 'da Silva Santos Júnior', 'neymar@gmail.com', curdate(), 'neymar', '12345678', true);
INSERT INTO usuarios (nombre, apellido, email, fecha_creacion, username, password, enabled) values ('Kylian', 'Mbappé Lottin', 'mbappe@gmail.com', curdate(), 'mbappe', '123456789123', true);

-- Cargamos información en la tabla productos.
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('LG monitor 24 pulgadas', 1500, NOW(), 10, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Sony cámara digital DSC-W320B', 2000, NOW(), 20, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Apple iPhone 12 pro máx', 3500, NOW(), 15, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Asus notebook N56VB', 3700.50, NOW(), 4, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('JBL parlante bluetooth', 900, NOW(), 2, 'a.png', true);

-- Insertamos algunas ventas.
INSERT INTO carritos (fecha_creacion, usuario_id) VALUES (NOW(), 1);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (2, 1, 1);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (3, 1, 4);
INSERT INTO carritos (fecha_creacion, usuario_id) VALUES (NOW(), 2);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (5, 2, 3);

-- Insertamos los roles.
INSERT INTO roles (nombre, descripcion) VALUES ('ROLE_USER', 'Puede comprar');
INSERT INTO roles (nombre, descripcion) VALUES ('ROLE_ADMIN', 'Administrador');

-- Insertamos las relaciones entre los usuarios y los roles.
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);