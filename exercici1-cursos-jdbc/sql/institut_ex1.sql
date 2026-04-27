DROP DATABASE IF EXISTS institut_ex1;
CREATE DATABASE institut_ex1_dam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE institut_ex1_dam;

CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60) NOT NULL,
    nivell VARCHAR(20) NOT NULL
);

INSERT INTO cursos (nom, nivell) VALUES
('Programació', '1r DAM'),
('Bases de Dades', '1r DAM'),
('Desenvolupament web', '1r DAW');
