DROP TRIGGER IF EXISTS "crear itinerario al crear usuario";
DROP TABLE IF EXISTS "compras_de_itinerarios";
DROP TABLE IF EXISTS "itinerarios";
DROP TABLE IF EXISTS "usuarios";
DROP TABLE IF EXISTS "atracciones_en_promocion";
DROP TABLE IF EXISTS "atracciones";
DROP TABLE IF EXISTS "promociones";
DROP TABLE IF EXISTS "tipos_de_promocion";
DROP TABLE IF EXISTS "tipos_de_atraccion";

--crear tipos de atraccion

CREATE TABLE "tipos_de_atraccion" (
	"tipo"	TEXT,
	PRIMARY KEY("tipo")
);

--crear tipos de promocion

CREATE TABLE "tipos_de_promocion" (
	"tipo"	TEXT,
	PRIMARY KEY("tipo")
);

--crear atracciones


CREATE TABLE "atracciones" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") >= 3) UNIQUE,
	"precio"	INTEGER NOT NULL CHECK("precio" > 0),
	"duracion"	INTEGER NOT NULL CHECK("duracion" > 0),
	"cupo"	INTEGER NOT NULL CHECK("cupo" >= 0),
	"fk_tipo_atraccion"	TEXT NOT NULL,
	FOREIGN KEY("fk_tipo_atraccion") REFERENCES "tipos_de_atraccion"("tipo"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

--crear promociones


CREATE TABLE "promociones" (
	"id"	INTEGER,
	"tipo_promocion"	TEXT NOT NULL,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") > 3) UNIQUE,
	"descuento"	INTEGER NOT NULL CHECK("descuento" > 0),
	FOREIGN KEY("tipo_promocion") REFERENCES "tipos_de_promocion"("tipo"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

--crear atracciones en promocion

CREATE TABLE "atracciones_en_promocion" (
	"fk_promocion"	INTEGER NOT NULL,
	"fk_atraccion"	INTEGER NOT NULL,
	FOREIGN KEY("fk_promocion") REFERENCES "promociones"("id") ON DELETE CASCADE,
	FOREIGN KEY("fk_atraccion") REFERENCES "atracciones"("id"),
	UNIQUE("fk_promocion","fk_atraccion")
);

--crear usuarios

CREATE TABLE "usuarios" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") > 3),
	"dinero_disp"	INTEGER NOT NULL DEFAULT 0 CHECK("dinero_disp" >= 0),
	"tiempo_disp"	REAL NOT NULL DEFAULT 0 CHECK("tiempo_disp" >= 0),
	"tipo_preferencia"	TEXT NOT NULL,
	FOREIGN KEY("tipo_preferencia") REFERENCES "tipos_de_atraccion"("tipo"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

--crear itinerarios

CREATE TABLE "itinerarios" (
	"id"	INTEGER,
	"costo"	INTEGER NOT NULL DEFAULT 0 CHECK("costo" >= 0),
	"duracion"	REAL NOT NULL DEFAULT 0 CHECK("duracion" >= 0),
	FOREIGN KEY("id") REFERENCES "usuarios"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);

--crear compras de itinerarios

CREATE TABLE "compras_de_itinerarios" (
	"fk_itinerario"	INTEGER NOT NULL,
	"fk_promocion"	INTEGER CHECK("fk_promocion" IS null AND "fk_atraccion" IS NOT null OR "fk_promocion" IS NOT null AND "fk_atraccion" IS null),
	"fk_atraccion"	INTEGER CHECK("fk_promocion" IS null AND "fk_atraccion" IS NOT null OR "fk_promocion" IS NOT null AND "fk_atraccion" IS null),
	FOREIGN KEY("fk_atraccion") REFERENCES "atracciones"("id"),
	FOREIGN KEY("fk_promocion") REFERENCES "promociones"("id"),
	FOREIGN KEY("fk_itinerario") REFERENCES "itinerarios"("id") ON DELETE CASCADE,
	UNIQUE("fk_itinerario","fk_atraccion"),
	UNIQUE("fk_itinerario","fk_promocion")
);

--crear trigger

CREATE TRIGGER 'crear itinerario al crear usuario'
AFTER INSERT ON usuarios
BEGIN
INSERT INTO itinerarios (id)
VALUES (NEW.id);
END;

-- cargar datos: 

INSERT INTO tipos_de_atraccion (tipo) 
VALUES ('aventuras'),
('degustacion'),
('paisaje');

INSERT INTO tipos_de_promocion(tipo) 
VALUES ('porcentual'),
('absoluta'),
('axb');

INSERT INTO atracciones (nombre, precio, duracion, cupo, fk_tipo_atraccion) 
VALUES ('Moria', '10', '2', '6', 'aventuras'),
('Minas Tirith', '3', '2.5', '25', 'paisaje'),
('La Comarca', '3', '6.5',	'150', 'degustacion'),
('Mordor', '25', '3', '4', 'aventuras'),
('Abismo de Helm', '6' , '2', '15', 'paisaje'),
('Lothloriem', '35', '1', '30', 'degustacion'),
('Erebor', '12' , '3', '1', 'paisaje'),
('Bosque Negro', '3', '4', '12', 'aventuras');

INSERT INTO promociones (tipo_promocion, nombre, descuento) 
VALUES ('porcentual', 'Pack Aventuras', '20'),
('absoluta', 'Pack Degustacion', '36'),
('axb', 'Pack Paisaje', '2');

INSERT INTO atracciones_en_promocion (fk_promocion, fk_atraccion) 
VALUES ('1', '4'),
('1', '8'),
('2', '3'),
('2', '6'),
('3', '2'),
('3', '5'),
('3', '7');

INSERT INTO usuarios (nombre, dinero_disp, tiempo_disp, tipo_preferencia) 
VALUES ('Martin Suarez', '15', '35.0', 'paisaje'),
('Jose Olaechea', '60', '35.0', 'degustacion'),
('Maria Cumillanca', '30', '8.0', 'aventuras'),
('Ana Farias', '35', '35.0', 'paisaje');