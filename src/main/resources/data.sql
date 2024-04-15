DROP TABLE IF EXISTS tenistas;
DROP TABLE IF EXISTS torneo;
DROP TABLE IF EXISTS categoria;

CREATE TABLE tenistas
(
    ranking INT PRIMARY KEY,
    nombreCompleto VARCHAR(50) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    fechaNac DATE NOT NULL,
    edad INT NOT NULL,
    altura DECIMAL(3, 2) NOT NULL,
    peso DECIMAL(4, 1) NOT NULL,
    fecha DATE NOT NULL,
    mano VARCHAR(50) NOT NULL,
    reves VARCHAR(50) NOT NULL,
    entrenado VARCHAR(50) NOT NULL,
    dineroGanado INT NOT NULL,
    bestRanking INT NOT NULL,
    wins INT NOT NULL,
    loses INT NOT NULL,
    winrate DECIMAL(4, 3) NOT NULL,
    imagen VARCHAR(50) NOT NULL
);

CREATE TABLE categoria
(
    id INT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE torneo
(
    id UUID PRIMARY KEY,
    ubicacion VARCHAR(255),
    categoria VARCHAR(50),
    categoria_id INT,
    fechaInicio DATE,
    fechaFin DATE,
    superficie VARCHAR(50),
    premio BIGINT,
    entradas BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

INSERT INTO tenistas (ranking, nombreCompleto, pais, fechaNac, edad, altura, peso, fecha, mano, reves, entrenado, dineroGanado, bestRanking, wins, loses, winrate, imagen)
VALUES
    (1, 'Roger Federer', 'Suiza', '1981-08-08', 42, 1.85, 85.5, '2024-04-08', 'DIESTRO', 'UNA_MANO', 'Xavier Malisse', 129002519, 1, 1083, 272, 0.799, 'roger_federer.jpg'),
    (2, 'Rafael Nadal', 'España', '1986-06-03', 37, 1.85, 85.0, '2024-04-08', 'ZURDO', 'DOS_MANOS', 'Toni Nadal', 123825005, 1, 1002, 207, 0.829, 'rafael_nadal.jpg'),
    (3, 'Novak Djokovic', 'Serbia', '1987-05-22', 36, 1.88, 77.0, '2024-04-08', 'DIESTRO', 'DOS_MANOS', 'Marian Vajda', 151656789, 1, 1068, 206, 0.838, 'novak_djokovic.jpg');
INSERT INTO categoria (id,nombre) VALUES
                                   (1,'Master 1000'),
                                   (2,'Master 500'),
                                   (3,'Master 250');
INSERT INTO torneo (id, ubicacion, categoria, categoria_id, fechaInicio, fechaFin, superficie, premio, entradas)
VALUES
    ('f47b1b1b-1b1b-1b1b-1b1b-1b1b1b1bab1b', 'Madrid', 'Masters 1000', 1, '2024-04-29', '2024-05-12', 'Tierra batida', 7000000, 1000),
    ('f47b1b1b-1b1b-1b1b-1b1b-1b141b1b1b1c', 'Barcelona', 'ATP 500', 2, '2024-04-22', '2024-04-28', 'Tierra batida', 2500000, 500),
    ('f47b1b1b-1b1b-1b1b-1b1b-1b1b5b1b1b1d', 'Estoril', 'ATP 250', 3, '2024-04-29', '2024-05-05', 'Tierra batida', 500000, 250);
