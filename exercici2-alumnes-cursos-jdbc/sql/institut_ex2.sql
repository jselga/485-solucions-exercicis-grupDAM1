DROP DATABASE IF EXISTS institut_ex2_dam;
CREATE DATABASE institut_ex2_dam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE institut_ex2_dam;

CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60) NOT NULL,
    nivell VARCHAR(20) NOT NULL
);

CREATE TABLE alumnes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE,
    curs_id INT NOT NULL,
    CONSTRAINT fk_alumnes_cursos FOREIGN KEY (curs_id) REFERENCES cursos(id)
);

INSERT INTO cursos (nom, nivell) VALUES
('Programació', '1r DAM'),
('Bases de Dades', '1r DAM'),
('Desenvolupament web', '1r DAW');

INSERT INTO alumnes (nom, email, curs_id) VALUES
('Laia', 'laia@institut.cat', 1),
('Marc', 'marc@institut.cat', 1),
('Nora', 'nora@institut.cat', 3);
