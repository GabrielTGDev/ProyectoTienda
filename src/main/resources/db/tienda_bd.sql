
CREATE DATABASE IF NOT EXISTS Tienda CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE Tienda;

CREATE TABLE Categorias (
    id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Usuarios (
    id INT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE Direcciones (
    usuario_id INT,
    calle VARCHAR(100),
    ciudad VARCHAR(50),
    codigo_postal VARCHAR(10),
    PRIMARY KEY(usuario_id),
    FOREIGN KEY(usuario_id) REFERENCES Usuarios(id)
);

CREATE TABLE Productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(50),
    precio DECIMAL(10,2),
    categoria_id INT,
    FOREIGN KEY(categoria_id) REFERENCES Categorias(id)
);

CREATE TABLE Pedidos (
    id INT PRIMARY KEY,
    usuario_id INT,
    producto_id INT,
    cantidad INT,
    fecha DATE,
    FOREIGN KEY(usuario_id) REFERENCES Usuarios(id),
    FOREIGN KEY(producto_id) REFERENCES Productos(id)
);
INSERT INTO Categorias VALUES (1, 'Electrónica');
INSERT INTO Categorias VALUES (2, 'Ropa');
INSERT INTO Categorias VALUES (3, 'Hogar');
INSERT INTO Categorias VALUES (4, 'Libros');
INSERT INTO Categorias VALUES (5, 'Juguetes');
INSERT INTO Usuarios VALUES (1, 'Rosa', 'Cadenas', 'felipasamper@gual.es');
INSERT INTO Usuarios VALUES (2, 'Juanito', 'Ocaña', 'icaballero@hotmail.com');
INSERT INTO Usuarios VALUES (3, 'Carmela', 'Garay', 'quiroscustodia@gmail.com');
INSERT INTO Usuarios VALUES (4, 'Saturnino', 'Cabrero', 'aranzazuperez@yahoo.com');
INSERT INTO Usuarios VALUES (5, 'Leandra', 'Cazorla', 'telmoizquierdo@hotmail.com');
INSERT INTO Usuarios VALUES (6, 'Horacio', 'Valderrama', 'solareyes@escamilla-espanol.es');
INSERT INTO Usuarios VALUES (7, 'Alma', 'Cantero', 'emenendez@hotmail.com');
INSERT INTO Usuarios VALUES (8, 'Gertrudis', 'Bustos', 'dnino@hotmail.com');
INSERT INTO Usuarios VALUES (9, 'Lucio', 'Torrijos', 'cortinaemiliana@gmail.com');
INSERT INTO Usuarios VALUES (10, 'Leocadio', 'Barrena', 'curroribas@yahoo.com');
INSERT INTO Usuarios VALUES (11, 'Cintia', 'Murcia', 'xcastello@hotmail.com');
INSERT INTO Usuarios VALUES (12, 'Ramón', 'Seguí', 'isabel03@hotmail.com');
INSERT INTO Usuarios VALUES (13, 'Bernarda', 'Cózar', 'venceslas48@miranda-guitart.es');
INSERT INTO Usuarios VALUES (14, 'Balduino', 'Cabello', 'iban74@barreda-puerta.org');
INSERT INTO Usuarios VALUES (15, 'Amílcar', 'Losa', 'arandaamancio@hotmail.com');
INSERT INTO Usuarios VALUES (16, 'Eva María', 'Aguilar', 'david07@yahoo.com');
INSERT INTO Usuarios VALUES (17, 'Francisco Jose', 'Verdú', 'nicodemoreguera@gmail.com');
INSERT INTO Usuarios VALUES (18, 'Teófilo', 'Sureda', 'blazquezjosue@yahoo.com');
INSERT INTO Usuarios VALUES (19, 'Baudelio', 'Coca', 'celsojaen@hotmail.com');
INSERT INTO Usuarios VALUES (20, 'Rosa', 'Vall', 'cervantesmaximino@hotmail.com');
INSERT INTO Usuarios VALUES (21, 'Macarena', 'Carro', 'pcabo@hotmail.com');
INSERT INTO Usuarios VALUES (22, 'Xavier', 'Casanova', 'castrillonazaret@alfonso-ochoa.org');
INSERT INTO Usuarios VALUES (23, 'René', 'Galan', 'folchheriberto@yahoo.com');
INSERT INTO Usuarios VALUES (24, 'Edgardo', 'Marqués', 'avila@hotmail.com');
INSERT INTO Usuarios VALUES (25, 'Hugo', 'Vega', 'amador14@aguado.org');
INSERT INTO Usuarios VALUES (26, 'Fabián', 'Calderón', 'maxi67@montalban.es');
INSERT INTO Usuarios VALUES (27, 'Quirino', 'Robledo', 'modestomanzanares@yahoo.com');
INSERT INTO Usuarios VALUES (28, 'Ruperto', 'Castellanos', 'guerrarolando@hidalgo.org');
INSERT INTO Usuarios VALUES (29, 'Albert', 'Ruano', 'vinicioaliaga@diez.es');
INSERT INTO Usuarios VALUES (30, 'Eustaquio', 'Matas', 'fidelmas@arana.com');
INSERT INTO Usuarios VALUES (31, 'Fortunata', 'Coloma', 'gfigueroa@ballester.org');
INSERT INTO Usuarios VALUES (32, 'Ester', 'Mármol', 'teclamoran@gmail.com');
INSERT INTO Usuarios VALUES (33, 'Eliseo', 'Viñas', 'castellanosvenceslas@dominguez.org');
INSERT INTO Usuarios VALUES (34, 'Reyes', 'Molina', 'adora41@gmail.com');
INSERT INTO Usuarios VALUES (35, 'Marco', 'Cuevas', 'vleiva@hotmail.com');
INSERT INTO Usuarios VALUES (36, 'Consuela', 'Marqués', 'patricio14@franch.com');
INSERT INTO Usuarios VALUES (37, 'Yolanda', 'Segura', 'qarcos@gmail.com');
INSERT INTO Usuarios VALUES (38, 'Amalia', 'Miranda', 'ferrandoflorinda@arnau.es');
INSERT INTO Usuarios VALUES (39, 'Mariano', 'Benavente', 'sergioexposito@alcaraz.net');
INSERT INTO Usuarios VALUES (40, 'Liliana', 'Pallarès', 'ibanos@gmail.com');
INSERT INTO Usuarios VALUES (41, 'Oriana', 'Luque', 'garaysaturnina@carrion-gracia.com');
INSERT INTO Usuarios VALUES (42, 'Jenaro', 'Sanchez', 'ricardagabaldon@iniesta.org');
INSERT INTO Usuarios VALUES (43, 'Chus', 'Rozas', 'toribio12@flores.es');
INSERT INTO Usuarios VALUES (44, 'Édgar', 'Cordero', 'marquezulises@yahoo.com');
INSERT INTO Usuarios VALUES (45, 'Wálter', 'Giménez', 'guillermoangulo@barcelo.com');
INSERT INTO Usuarios VALUES (46, 'Alejandra', 'Grau', 'catalinaarcos@hotmail.com');
INSERT INTO Usuarios VALUES (47, 'Nicanor', 'Casares', 'iaparicio@hotmail.com');
INSERT INTO Usuarios VALUES (48, 'Eusebia', 'Nicolás', 'cortessandra@millan-moliner.net');
INSERT INTO Usuarios VALUES (49, 'Guiomar', 'Molins', 'estefaniapareja@gmail.com');
INSERT INTO Usuarios VALUES (50, 'Bernardita', 'Tomás', 'gervasio44@yahoo.com');
INSERT INTO Direcciones VALUES (1, 'C. Jovita Rosa 18 Piso 3 ', 'La Rioja', '18144');
INSERT INTO Direcciones VALUES (2, 'Ronda Abigaíl Alcalde 31 Apt. 39 ', 'Baleares', '84056');
INSERT INTO Direcciones VALUES (3, 'Pasadizo de Azahara Cabrera 2 Puerta 2 ', 'Las Palmas', '87181');
INSERT INTO Direcciones VALUES (4, 'Avenida Borja Roselló 64 Puerta 5 ', 'Navarra', '49801');
INSERT INTO Direcciones VALUES (5, 'C. de Saturnina Navas 51', 'La Coruña', '09123');
INSERT INTO Direcciones VALUES (6, 'Urbanización Reina Valdés 47', 'Girona', '75706');
INSERT INTO Direcciones VALUES (7, 'Callejón de Marcelo Garzón 935', 'Melilla', '99386');
INSERT INTO Direcciones VALUES (8, 'Paseo de Adelina Verdejo 9', 'Almería', '22003');
INSERT INTO Direcciones VALUES (9, 'Pasaje Teo Artigas 2', 'Ceuta', '52160');
INSERT INTO Direcciones VALUES (10, 'Pasadizo de Rita Barreda 12 Puerta 5 ', 'Girona', '87683');
INSERT INTO Direcciones VALUES (11, 'Pasadizo Vicenta Salinas 5', 'Cádiz', '83233');
INSERT INTO Direcciones VALUES (12, 'Avenida Juan José Toro 349', 'Soria', '84123');
INSERT INTO Direcciones VALUES (13, 'Via Federico Valero 31', 'Córdoba', '68099');
INSERT INTO Direcciones VALUES (14, 'Urbanización Haroldo Garriga 16 Piso 7 ', 'Albacete', '35216');
INSERT INTO Direcciones VALUES (15, 'Callejón Adela Fabregat 58', 'Córdoba', '31818');
INSERT INTO Direcciones VALUES (16, 'Paseo de Marciano Amor 835 Puerta 5 ', 'Melilla', '04071');
INSERT INTO Direcciones VALUES (17, 'Acceso de Paloma Ibáñez 84', 'Palencia', '70246');
INSERT INTO Direcciones VALUES (18, 'Vial de Carlota Busquets 24 Puerta 6 ', 'Las Palmas', '74157');
INSERT INTO Direcciones VALUES (19, 'Ronda María Luisa Galan 670', 'Ciudad', '33907');
INSERT INTO Direcciones VALUES (20, 'Vial de Margarita Orozco 60 Apt. 94 ', 'Cantabria', '12052');
INSERT INTO Direcciones VALUES (21, 'Avenida Juanita Cervantes 32 Puerta 5 ', 'Barcelona', '32735');
INSERT INTO Direcciones VALUES (22, 'Paseo de Nydia Pombo 8', 'Las Palmas', '44756');
INSERT INTO Direcciones VALUES (23, 'Via Feliciano Fiol 5', 'Palencia', '23414');
INSERT INTO Direcciones VALUES (24, 'Callejón de Catalina Requena 5 Apt. 57 ', 'Madrid', '28217');
INSERT INTO Direcciones VALUES (25, 'C. de Rebeca Torrijos 888', 'Cáceres', '39204');
INSERT INTO Direcciones VALUES (26, 'Cuesta de Loreto Solera 923', 'Barcelona', '15630');
INSERT INTO Direcciones VALUES (27, 'Cañada de Margarita Medina 68', 'Ávila', '76097');
INSERT INTO Direcciones VALUES (28, 'Cuesta de Guadalupe Alfaro 611', 'Castellón', '32846');
INSERT INTO Direcciones VALUES (29, 'Pasaje de Aura Aguiló 2 Puerta 2 ', 'Córdoba', '62117');
INSERT INTO Direcciones VALUES (30, 'Callejón Reynaldo Rocha 87', 'Jaén', '62228');
INSERT INTO Direcciones VALUES (31, 'Callejón de Ileana Catalán 26', 'Sevilla', '90399');
INSERT INTO Direcciones VALUES (32, 'Cuesta Marcio Costa 55 Piso 7 ', 'Badajoz', '70386');
INSERT INTO Direcciones VALUES (33, 'Alameda Saturnina Pino 94', 'Murcia', '38074');
INSERT INTO Direcciones VALUES (34, 'Pasaje Remedios Sabater 7', 'Málaga', '81686');
INSERT INTO Direcciones VALUES (35, 'Cañada de Domitila Leiva 601 Piso 3 ', 'Álava', '18773');
INSERT INTO Direcciones VALUES (36, 'Acceso de Sandalio Amigó 5', 'Girona', '58969');
INSERT INTO Direcciones VALUES (37, 'Alameda Ramona Soler 1', 'Cádiz', '12537');
INSERT INTO Direcciones VALUES (38, 'Paseo de Leyre Morata 5 Apt. 68 ', 'La Coruña', '83536');
INSERT INTO Direcciones VALUES (39, 'Paseo de Adán Vicens 80 Puerta 2 ', 'La Rioja', '33405');
INSERT INTO Direcciones VALUES (40, 'Alameda Almudena Castillo 348', 'Zaragoza', '33975');
INSERT INTO Direcciones VALUES (41, 'Camino de Samu Vara 51 Puerta 4 ', 'Girona', '74810');
INSERT INTO Direcciones VALUES (42, 'C. de Adrián Olivé 703 Puerta 6 ', 'Tarragona', '74309');
INSERT INTO Direcciones VALUES (43, 'Urbanización Geraldo Miguel 43', 'Ciudad', '82652');
INSERT INTO Direcciones VALUES (44, 'Callejón de Pili Peiró 84', 'Toledo', '92565');
INSERT INTO Direcciones VALUES (45, 'Avenida Ruth Neira 3 Piso 9 ', 'Alicante', '09933');
INSERT INTO Direcciones VALUES (46, 'C. de Nuria Santos 4', 'Guipúzcoa', '52819');
INSERT INTO Direcciones VALUES (47, 'Ronda Plinio Escrivá 41', 'Alicante', '36954');
INSERT INTO Direcciones VALUES (48, 'C. Fortunato Torrents 66 Puerta 8 ', 'Alicante', '62190');
INSERT INTO Direcciones VALUES (49, 'Paseo Emilia Soria 19', 'Valladolid', '05992');
INSERT INTO Direcciones VALUES (50, 'Pasadizo Chus Espinosa 42', 'Salamanca', '97317');
INSERT INTO Productos VALUES (1, 'Reprehenderit', 137.53, 5);
INSERT INTO Productos VALUES (2, 'Fugiat', 351.03, 5);
INSERT INTO Productos VALUES (3, 'Facere', 69.55, 1);
INSERT INTO Productos VALUES (4, 'Vel', 90.31, 5);
INSERT INTO Productos VALUES (5, 'Laborum', 400.03, 3);
INSERT INTO Productos VALUES (6, 'Eum', 473.19, 5);
INSERT INTO Productos VALUES (7, 'Ipsum', 429.07, 1);
INSERT INTO Productos VALUES (8, 'Corrupti', 60.24, 1);
INSERT INTO Productos VALUES (9, 'Aspernatur', 220.88, 3);
INSERT INTO Productos VALUES (10, 'Eligendi', 57.05, 4);
INSERT INTO Productos VALUES (11, 'Iusto', 100.74, 5);
INSERT INTO Productos VALUES (12, 'Ea', 335.28, 4);
INSERT INTO Productos VALUES (13, 'In', 354.0, 1);
INSERT INTO Productos VALUES (14, 'Rem', 423.61, 3);
INSERT INTO Productos VALUES (15, 'Neque', 412.03, 1);
INSERT INTO Productos VALUES (16, 'Libero', 159.43, 4);
INSERT INTO Productos VALUES (17, 'Deserunt', 76.51, 3);
INSERT INTO Productos VALUES (18, 'Velit', 405.69, 2);
INSERT INTO Productos VALUES (19, 'Distinctio', 55.0, 2);
INSERT INTO Productos VALUES (20, 'Fugit', 455.83, 3);
INSERT INTO Productos VALUES (21, 'Rerum', 277.22, 2);
INSERT INTO Productos VALUES (22, 'Et', 250.28, 5);
INSERT INTO Productos VALUES (23, 'Enim', 24.45, 3);
INSERT INTO Productos VALUES (24, 'Neque', 114.37, 3);
INSERT INTO Productos VALUES (25, 'Expedita', 205.82, 2);
INSERT INTO Productos VALUES (26, 'Asperiores', 175.55, 4);
INSERT INTO Productos VALUES (27, 'Perspiciatis', 53.44, 3);
INSERT INTO Productos VALUES (28, 'At', 452.93, 1);
INSERT INTO Productos VALUES (29, 'Cupiditate', 354.9, 2);
INSERT INTO Productos VALUES (30, 'Amet', 441.75, 2);
INSERT INTO Productos VALUES (31, 'Tempore', 206.83, 2);
INSERT INTO Productos VALUES (32, 'Maxime', 383.38, 5);
INSERT INTO Productos VALUES (33, 'Numquam', 185.31, 4);
INSERT INTO Productos VALUES (34, 'Eos', 263.0, 3);
INSERT INTO Productos VALUES (35, 'Eum', 426.13, 3);
INSERT INTO Productos VALUES (36, 'Doloremque', 121.98, 4);
INSERT INTO Productos VALUES (37, 'Dolore', 153.44, 4);
INSERT INTO Productos VALUES (38, 'Quis', 433.21, 3);
INSERT INTO Productos VALUES (39, 'Doloremque', 227.36, 2);
INSERT INTO Productos VALUES (40, 'Maiores', 178.77, 2);
INSERT INTO Productos VALUES (41, 'Deserunt', 240.62, 3);
INSERT INTO Productos VALUES (42, 'Perferendis', 196.88, 2);
INSERT INTO Productos VALUES (43, 'Tempora', 424.98, 1);
INSERT INTO Productos VALUES (44, 'Tempora', 74.73, 3);
INSERT INTO Productos VALUES (45, 'Aliquam', 490.38, 2);
INSERT INTO Productos VALUES (46, 'Magni', 77.03, 1);
INSERT INTO Productos VALUES (47, 'Optio', 65.12, 4);
INSERT INTO Productos VALUES (48, 'Reiciendis', 202.65, 3);
INSERT INTO Productos VALUES (49, 'Ex', 324.63, 3);
INSERT INTO Productos VALUES (50, 'Dignissimos', 358.18, 2);
INSERT INTO Pedidos VALUES (1, 47, 33, 3, '2024-12-18');
INSERT INTO Pedidos VALUES (2, 37, 22, 4, '2024-08-03');
INSERT INTO Pedidos VALUES (3, 24, 2, 5, '2025-03-09');
INSERT INTO Pedidos VALUES (4, 38, 8, 2, '2024-11-15');
INSERT INTO Pedidos VALUES (5, 41, 31, 3, '2024-12-02');
INSERT INTO Pedidos VALUES (6, 16, 1, 2, '2025-01-02');
INSERT INTO Pedidos VALUES (7, 1, 31, 5, '2024-08-02');
INSERT INTO Pedidos VALUES (8, 44, 27, 5, '2024-05-21');
INSERT INTO Pedidos VALUES (9, 49, 9, 5, '2024-12-08');
INSERT INTO Pedidos VALUES (10, 1, 2, 2, '2024-05-08');
INSERT INTO Pedidos VALUES (11, 48, 2, 2, '2024-09-17');
INSERT INTO Pedidos VALUES (12, 16, 44, 1, '2024-05-13');
INSERT INTO Pedidos VALUES (13, 27, 6, 5, '2024-11-13');
INSERT INTO Pedidos VALUES (14, 6, 12, 2, '2024-10-03');
INSERT INTO Pedidos VALUES (15, 8, 28, 2, '2024-07-16');
INSERT INTO Pedidos VALUES (16, 2, 46, 5, '2024-07-06');
INSERT INTO Pedidos VALUES (17, 22, 41, 1, '2025-02-16');
INSERT INTO Pedidos VALUES (18, 9, 50, 3, '2024-05-02');
INSERT INTO Pedidos VALUES (19, 28, 1, 2, '2024-11-12');
INSERT INTO Pedidos VALUES (20, 1, 47, 4, '2024-11-07');
INSERT INTO Pedidos VALUES (21, 21, 29, 2, '2025-03-23');
INSERT INTO Pedidos VALUES (22, 4, 39, 3, '2024-05-16');
INSERT INTO Pedidos VALUES (23, 27, 44, 4, '2024-12-05');
INSERT INTO Pedidos VALUES (24, 2, 47, 2, '2024-06-28');
INSERT INTO Pedidos VALUES (25, 49, 36, 2, '2025-02-19');
INSERT INTO Pedidos VALUES (26, 32, 33, 5, '2025-03-11');
INSERT INTO Pedidos VALUES (27, 7, 8, 3, '2024-08-11');
INSERT INTO Pedidos VALUES (28, 30, 11, 3, '2024-08-09');
INSERT INTO Pedidos VALUES (29, 36, 12, 5, '2024-04-23');
INSERT INTO Pedidos VALUES (30, 1, 23, 3, '2024-06-04');
INSERT INTO Pedidos VALUES (31, 16, 42, 4, '2025-03-20');
INSERT INTO Pedidos VALUES (32, 31, 42, 3, '2024-06-02');
INSERT INTO Pedidos VALUES (33, 45, 36, 5, '2024-11-18');
INSERT INTO Pedidos VALUES (34, 18, 29, 1, '2024-09-12');
INSERT INTO Pedidos VALUES (35, 17, 31, 4, '2024-09-06');
INSERT INTO Pedidos VALUES (36, 2, 24, 2, '2025-02-13');
INSERT INTO Pedidos VALUES (37, 43, 20, 5, '2025-01-28');
INSERT INTO Pedidos VALUES (38, 24, 46, 2, '2025-03-07');
INSERT INTO Pedidos VALUES (39, 18, 20, 2, '2025-02-22');
INSERT INTO Pedidos VALUES (40, 11, 8, 3, '2024-08-06');
INSERT INTO Pedidos VALUES (41, 40, 25, 5, '2024-07-08');
INSERT INTO Pedidos VALUES (42, 43, 18, 5, '2025-03-24');
INSERT INTO Pedidos VALUES (43, 31, 2, 5, '2024-07-10');
INSERT INTO Pedidos VALUES (44, 15, 8, 3, '2024-12-17');
INSERT INTO Pedidos VALUES (45, 37, 39, 1, '2025-02-15');
INSERT INTO Pedidos VALUES (46, 11, 17, 5, '2024-09-02');
INSERT INTO Pedidos VALUES (47, 4, 21, 3, '2025-01-20');
INSERT INTO Pedidos VALUES (48, 22, 15, 2, '2024-04-14');
INSERT INTO Pedidos VALUES (49, 18, 36, 5, '2025-02-03');
INSERT INTO Pedidos VALUES (50, 43, 38, 4, '2025-03-31');
INSERT INTO Pedidos VALUES (51, 19, 5, 4, '2025-02-03');
INSERT INTO Pedidos VALUES (52, 34, 16, 3, '2024-12-14');
INSERT INTO Pedidos VALUES (53, 42, 28, 5, '2024-05-22');
INSERT INTO Pedidos VALUES (54, 17, 25, 2, '2024-09-25');
INSERT INTO Pedidos VALUES (55, 37, 25, 3, '2024-05-24');
INSERT INTO Pedidos VALUES (56, 3, 7, 4, '2024-09-04');
INSERT INTO Pedidos VALUES (57, 26, 9, 5, '2024-10-21');
INSERT INTO Pedidos VALUES (58, 12, 41, 4, '2024-10-07');
INSERT INTO Pedidos VALUES (59, 22, 43, 4, '2025-01-28');
INSERT INTO Pedidos VALUES (60, 10, 29, 5, '2024-11-26');
INSERT INTO Pedidos VALUES (61, 27, 19, 3, '2025-03-17');
INSERT INTO Pedidos VALUES (62, 42, 19, 2, '2024-09-08');
INSERT INTO Pedidos VALUES (63, 23, 36, 5, '2024-08-03');
INSERT INTO Pedidos VALUES (64, 14, 4, 1, '2024-05-08');
INSERT INTO Pedidos VALUES (65, 23, 8, 2, '2024-05-20');
INSERT INTO Pedidos VALUES (66, 22, 25, 4, '2024-04-28');
INSERT INTO Pedidos VALUES (67, 22, 41, 3, '2024-12-24');
INSERT INTO Pedidos VALUES (68, 42, 46, 3, '2025-04-02');
INSERT INTO Pedidos VALUES (69, 30, 15, 1, '2025-03-29');
INSERT INTO Pedidos VALUES (70, 3, 22, 2, '2024-12-27');
INSERT INTO Pedidos VALUES (71, 26, 27, 4, '2024-08-18');
INSERT INTO Pedidos VALUES (72, 6, 16, 5, '2024-06-30');
INSERT INTO Pedidos VALUES (73, 50, 1, 3, '2024-04-19');
INSERT INTO Pedidos VALUES (74, 40, 30, 1, '2024-09-25');
INSERT INTO Pedidos VALUES (75, 33, 25, 1, '2025-04-04');
INSERT INTO Pedidos VALUES (76, 29, 50, 4, '2024-04-27');
INSERT INTO Pedidos VALUES (77, 22, 26, 3, '2024-06-11');
INSERT INTO Pedidos VALUES (78, 12, 17, 5, '2025-01-29');
INSERT INTO Pedidos VALUES (79, 29, 33, 5, '2024-11-13');
INSERT INTO Pedidos VALUES (80, 1, 22, 2, '2024-10-05');
INSERT INTO Pedidos VALUES (81, 42, 44, 2, '2024-09-11');
INSERT INTO Pedidos VALUES (82, 20, 16, 2, '2025-02-24');
INSERT INTO Pedidos VALUES (83, 16, 47, 5, '2024-06-01');
INSERT INTO Pedidos VALUES (84, 28, 26, 2, '2024-06-19');
INSERT INTO Pedidos VALUES (85, 13, 9, 1, '2024-05-04');
INSERT INTO Pedidos VALUES (86, 23, 4, 2, '2024-08-21');
INSERT INTO Pedidos VALUES (87, 19, 18, 2, '2024-10-10');
INSERT INTO Pedidos VALUES (88, 7, 4, 5, '2024-09-29');
INSERT INTO Pedidos VALUES (89, 7, 10, 2, '2024-12-31');
INSERT INTO Pedidos VALUES (90, 28, 37, 3, '2024-07-02');
INSERT INTO Pedidos VALUES (91, 11, 14, 5, '2024-04-27');
INSERT INTO Pedidos VALUES (92, 47, 37, 3, '2024-05-12');
INSERT INTO Pedidos VALUES (93, 45, 33, 2, '2024-07-04');
INSERT INTO Pedidos VALUES (94, 5, 42, 2, '2024-10-28');
INSERT INTO Pedidos VALUES (95, 27, 41, 5, '2025-03-18');
INSERT INTO Pedidos VALUES (96, 29, 46, 3, '2024-12-21');
INSERT INTO Pedidos VALUES (97, 18, 15, 2, '2024-06-21');
INSERT INTO Pedidos VALUES (98, 2, 37, 3, '2024-05-03');
INSERT INTO Pedidos VALUES (99, 34, 11, 3, '2024-08-16');
INSERT INTO Pedidos VALUES (100, 38, 25, 3, '2024-08-15');
