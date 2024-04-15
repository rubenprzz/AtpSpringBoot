DROP TABLE IF EXISTS tenistas;
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

INSERT INTO tenistas (ranking, nombreCompleto, pais, fechaNac, edad, altura, peso, fecha, mano, reves, entrenado, dineroGanado, bestRanking, wins, loses, winrate, imagen)
VALUES
    (1, 'Roger Federer', 'Suiza', '1981-08-08', 42, 1.85, 85.5, '2024-04-08', 'DIESTRO', 'UNA_MANO', 'Xavier Malisse', 129002519, 1, 1083, 272, 0.799, 'roger_federer.jpg'),
    (2, 'Rafael Nadal', 'España', '1986-06-03', 37, 1.85, 85.0, '2024-04-08', 'ZURDO', 'DOS_MANOS', 'Toni Nadal', 123825005, 1, 1002, 207, 0.829, 'rafael_nadal.jpg'),
    (3, 'Novak Djokovic', 'Serbia', '1987-05-22', 36, 1.88, 77.0, '2024-04-08', 'DIESTRO', 'DOS_MANOS', 'Marian Vajda', 151656789, 1, 1068, 206, 0.838, 'novak_djokovic.jpg');
