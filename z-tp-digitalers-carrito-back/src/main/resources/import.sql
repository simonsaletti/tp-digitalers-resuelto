-- Cargamos información en la tabla clientes.
-- INSERT INTO usuarios (nombre, apellido, email, fecha_creacion) VALUES ('Lionel', 'Messi', 'liomessi@gmail.com', NOW());
-- INSERT INTO usuarios (nombre, apellido, email, fecha_creacion) VALUES ('Edgar', 'Vivar', 'edgarvivar@gmail.com', NOW());

-- Cargamos información en la tabla productos.
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen) VALUES ('LG monitor 24 pulgadas', 1500, NOW(), 10, 'a.png');
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen) VALUES ('Sony cámara digital DSC-W320B', 2000, NOW(), 20, 'a.png');
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen) VALUES ('Apple iPhone 12 pro máx', 3500, NOW(), 15, 'a.png');
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen) VALUES ('Asus notebook N56VB', 3700.50, NOW(), 4, 'a.png');
INSERT INTO productos (descripcion, precio, fecha_creacion, stock, URLImagen) VALUES ('JBL parlante bluetooth', 900, NOW(), 2, 'a.png');

-- Insertamos algunas ventas.
-- INSERT INTO carritos (descripcion, observacion, fecha_creacion, usuario_id) VALUES ('Compra de equipos de oficina', null, NOW(), 1);
-- INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (2, 1, 1);
-- INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (3, 1, 4);
-- INSERT INTO carritos (descripcion, observacion, fecha_creacion, usuario_id) VALUES ('Compra de celulares', null, NOW(), 2);
-- INSERT INTO carritos_items (cantidad, carrito_id, producto_id) VALUES (5, 2, 3);

INSERT INTO usuarios (username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES ('andres', '$2a$10$tCnT1SDsyJWAi4UCr/XR0.x4hnDfQPEd6RbcnB13cN.XsfF/L4uCm', 1, 'Lionel', 'Messi', 'liomessi@gmail.com', NOW());
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email, fecha_creacion) VALUES ('admin', '$2a$10$DrQgPnG9xv4e49/qH77pReUZFsCnjDdGAuEFTCG2REWC..GYumoAu', 1, 'Edgar', 'Vivar', 'edgarvivar@gmail.com', NOW());

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);

