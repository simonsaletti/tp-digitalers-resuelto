-- Cargamos información en la tabla usuarios.
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES (1, 'edgarvivar', '$2a$10$DrQgPnG9xv4e49/qH77pReUZFsCnjDdGAuEFTCG2REWC..GYumoAu', 1, 'Edgar', 'Vivar', 'edgarvivar@gmail.com', NOW());
INSERT INTO usuarios (id, username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES (2, 'liomessi', '$2a$10$tCnT1SDsyJWAi4UCr/XR0.x4hnDfQPEd6RbcnB13cN.XsfF/L4uCm', 1, 'Lionel', 'Messi', 'liomessi@gmail.com', NOW());

-- Cargamos información en la tabla productos.
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('LG monitor 24 pulgadas', 1500, NOW(), 10, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Sony cámara digital DSC-W320B', 2000, NOW(), 20, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Apple iPhone 12 pro máx', 3500, NOW(), 15, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('Asus notebook N56VB', 3700.50, NOW(), 4, 'a.png', true);
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen, active) VALUES ('JBL parlante bluetooth', 900, NOW(), 2, 'a.png', true);

-- Insertamos algunas ventas.
INSERT INTO carritos (descripcion, observacion, fecha_creacion, usuario_id) VALUES ('Compra de equipos de oficina', null, NOW(), 1);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (2, 1, 1);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (3, 1, 4);
INSERT INTO carritos (descripcion, observacion, fecha_creacion, usuario_id) VALUES ('Compra de celulares', null, NOW(), 2);
INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (5, 2, 3);

-- Insertamos los roles.
INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

-- Insertamos las relaciones entre los usuarios y los roles.
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);

