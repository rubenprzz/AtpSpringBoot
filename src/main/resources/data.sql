DROP TABLE IF EXISTS tenistas;
DROP TABLE IF EXISTS torneo;

CREATE TABLE tenistas
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ranking bigint NOT NULL ,
    nombre_completo VARCHAR(50) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    edad INT NOT NULL,
    altura DECIMAL(3, 2) NOT NULL,
    peso DECIMAL(4, 1) NOT NULL,
    fecha DATE NOT NULL,
    mano VARCHAR(50) NOT NULL CHECK (mano IN ('DIESTRO', 'ZURDO')),
    reves VARCHAR(50) NOT NULL CHECK (reves IN ('UNA_MANO', 'DOS_MANOS')),
    entrenado VARCHAR(50) NOT NULL,
    dinero_ganado DOUBLE NOT NULL,
    best_ranking INT NOT NULL,
    wins INT NOT NULL,
    loses INT NOT NULL,
    winrate DECIMAL(6,3) NOT NULL ,
    imagen VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE torneo
(
    id UUID PRIMARY KEY,
    ubicacion VARCHAR(255),
    modo VARCHAR(50),
    categoria VARCHAR(50),
    fecha_inicio DATE,
    fecha_fin DATE,
    superficie VARCHAR(50),
    premio BIGINT,
    entradas BIGINT
);


INSERT INTO tenistas (ranking, nombre_completo, pais, fecha_nacimiento, edad, altura, peso, fecha, mano, reves, entrenado, dinero_ganado, best_ranking, wins, loses,winrate,  imagen, created_at, updated_at)
VALUES
    (1, 'Roger Federer', 'Suiza', '1981-08-08', 42, 1.85, 85.5, '2024-04-08', 'DIESTRO', 'UNA_MANO', 'Xavier Malisse', 1233.4, 1, 1083, 272,1,  'roger_federer.jpg', '2024-04-08', '2024-04-08'),
    (2, 'Rafael Nadal', 'España', '1986-06-03', 37, 1.85, 85.0, '2024-04-08', 'ZURDO', 'DOS_MANOS', 'Toni Nadal', 123825005, 1, 1002, 207 ,1,'rafael_nadal.jpg', '2024-04-08', '2024-04-08'),
    (3, 'Novak Djokovic', 'Serbia', '1987-05-22', 36, 1.88, 77.0, '2024-04-08', 'DIESTRO', 'DOS_MANOS', 'Marian Vajda', 151656789, 1, 1068, 206,1, 'novak_djokovic.jpg', '2024-04-08', '2024-04-08' );

INSERT INTO torneo (id, ubicacion, modo, categoria, fecha_inicio, fecha_fin, superficie, premio, entradas)
VALUES
    ('c2337f93-d017-45b0-90ec-638396e4e430', 'Madrid', 'INDIVIDUAL', 'MASTER_1000',  '2024-04-29', '2024-05-12', 'Tierra batida', 7000000, 1000),
    ('339292ff-b3f3-4421-9350-a4c47ab86d6d', 'Barcelona', 'DOBLE', 'MASTER_500', '2024-04-22', '2024-04-28', 'Tierra batida', 2500000, 500),
    ('0131ee32-d546-43d0-b234-c0d7c33ce040', 'Estoril', 'INDIVIDUAL_DOBLE',  'MASTER_250', '2024-04-29', '2024-05-05', 'Tierra batida', 500000, 250);
