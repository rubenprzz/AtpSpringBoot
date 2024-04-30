DROP TABLE IF EXISTS participante;
DROP TABLE IF EXISTS torneo;
DROP TABLE IF EXISTS tenistas;
DROP TABLE IF EXISTS user_entity_roles;
DROP TABLE IF EXISTS user_entity;
CREATE TABLE user_entity
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE tenistas
(
    id                bigint AUTO_INCREMENT PRIMARY KEY,
    ranking           bigint,
    nombre_completo   VARCHAR(50),
    pais              VARCHAR(50),
    fecha_nacimiento  DATE,
    edad              INT,
    altura            DECIMAL(3, 2),
    peso              DECIMAL(4, 1),
    fecha_profesional DATE,
    mano              VARCHAR(50)   CHECK (mano IN ('DIESTRO', 'ZURDO')),
    reves             VARCHAR(50)    CHECK (reves IN ('UNA_MANO', 'DOS_MANOS')),
    entrenador        VARCHAR(50)   ,
    dinero_ganado     DOUBLE        ,
    best_ranking      INT           ,
    modo              VARCHAR(50)    CHECK (modo IN ('INDIVIDUAL', 'DOBLE', 'INDIVIDUAL_DOBLE')),
    wins              INT           ,
    loses             INT          ,
    winrate           DECIMAL(4, 3) ,
    puntos            INT           ,
    imagen            VARCHAR(200)  ,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    torneo_id         UUID
);
CREATE TABLE user_entity_roles
(
    user_entity_id BIGINT,
    roles VARCHAR(50),
    FOREIGN KEY (user_entity_id) REFERENCES user_entity (id)
);

CREATE TABLE torneo
(
    id           UUID PRIMARY KEY,
    id_sec       BIGINT  UNIQUE ,
    ubicacion    VARCHAR(255),
    modo         VARCHAR(50)                   CHECK (modo IN ('INDIVIDUAL', 'DOBLE', 'INDIVIDUAL_DOBLE')),
    categoria    VARCHAR(50),
    fecha_inicio DATE,
    fecha_fin    DATE,
    superficie   VARCHAR(50),
    premio       BIGINT,
    entradas     BIGINT
);
CREATE TABLE participante
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    tenista_id       BIGINT,
    resultado        VARCHAR(255),
    puntos_otorgados BIGINT,
    torneo_id        UUID,
    FOREIGN KEY (tenista_id) REFERENCES tenistas (id),
    FOREIGN KEY (torneo_id) REFERENCES torneo (id)
);



INSERT INTO tenistas (ranking, nombre_completo, pais, fecha_nacimiento, edad, altura, peso, fecha_profesional, mano,
                      reves, entrenador, dinero_ganado, best_ranking, modo, wins, loses, winrate, puntos, imagen,
                      created_at, updated_at, torneo_id)
VALUES (1, 'Roger Federer', 'Suiza', '1981-08-08', 42, 1.85, 85.5, '2024-04-08', 'DIESTRO', 'UNA_MANO',
        'Xavier Malisse', 1233, 1, 'INDIVIDUAL', 1083, 272, 0.799, 1000,
        'https://www.insidehook.com/wp-content/uploads/2019/08/GettyImages-141508394.jpg', '2024-04-08', '2024-04-08',
        'c2337f93-d017-45b0-90ec-638396e4e430'),
       (2, 'Rafael Nadal', 'España', '1986-06-03', 37, 1.85, 85.0, '2024-04-08', 'ZURDO', 'DOS_MANOS', 'Toni Nadal',
        1238, 1, 'DOBLE', 1002, 207, 0.829, 500, 'rafael_nadal.jpg', '2024-04-08', '2024-04-08',
        '339292ff-b3f3-4421-9350-a4c47ab86d6d'),
       (3, 'Novak Djokovic', 'Serbia', '1987-05-22', 36, 1.88, 77.0, '2024-04-08', 'DIESTRO', 'DOS_MANOS',
        'Marian Vajda', 1516, 1, 'INDIVIDUAL_DOBLE', 1068, 206, 0.838, 300, 'novak_djokovic.jpg', '2024-04-08',
        '2024-04-08', '0131ee32-d546-43d0-b234-c0d7c33ce040');

INSERT INTO torneo (id, id_sec, ubicacion, modo, categoria, fecha_inicio, fecha_fin, superficie, premio, entradas)
VALUES ('c2337f93-d017-45b0-90ec-638396e4e430', 1, 'Madrid', 'INDIVIDUAL', 'MASTER_1000', '2024-04-29', '2024-05-12',
        'Tierra batida', 1000000, 1000),
       ('339292ff-b3f3-4421-9350-a4c47ab86d6d', 2, 'Barcelona', 'DOBLE', 'MASTER_500', '2024-04-22', '2024-04-28',
        'Tierra batida', 500000, 500),
       ('0131ee32-d546-43d0-b234-c0d7c33ce040', 3, 'Estoril', 'INDIVIDUAL_DOBLE', 'MASTER_250', '2024-04-29',
        '2024-05-05',
        'Tierra batida', 250000, 250);
-- Inserting user data
INSERT INTO user_entity (username, password, email,avatar)
VALUES ('admin', '$2a$10$jcZAOGBj7plA3cSlfU0XGuFtCwcYp/NYQhs6X24KmqkDHoObdhFiu', 'admin@sdf.com', 'admin_avatar.jpg'),
       ('testuser', '$2a$10$jcZAOGBj7plA3cSlfU0XGuFtCwcYp/NYQhs6X24KmqkDHoObdhFiu','user@sdf.com' , 'testuser_avatar.jpg'),
       ('admin_torneo', '$2a$10$jcZAOGBj7plA3cSlfU0XGuFtCwcYp/NYQhs6X24KmqkDHoObdhFiu', 'admin_torneo@torneo.com', 'admin_torneo_avatar.jpg'),
       ('admin_tenista', '$2a$10$jcZAOGBj7plA3cSlfU0XGuFtCwcYp/NYQhs6X24KmqkDHoObdhFiu', 'admin_tenista@tenisa.com', 'admin_tenista_avatar.jpg')
       ;



INSERT INTO user_entity_roles (user_entity_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (3,'ADMIN_TORNEO'),
       (4, 'ADMIN_TENISTA'),
       (2, 'USER');




