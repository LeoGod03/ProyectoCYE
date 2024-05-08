CREATE TABLE Licenciaturas(
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre VARCHAR(50) NOT NULL
);
	
CREATE TABLE Lista_cursos(
	id INT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	colegio VARCHAR(10) NOT NULL,
	ciclo VARCHAR(20) NOT NULL
);

CREATE TABLE Usuarios(
	correo VARCHAR(150) PRIMARY KEY,
	contraseña VARCHAR(100) NOT NULL,
	llave INT NOT NULL,
	rol VARCHAR(50) NOT NULL
);

CREATE TABLE Alumnos_registrados(
	matricula VARCHAR(12) PRIMARY KEY,
	nombre VARCHAR(75) NOT NULL,
	apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
	id_carrera INT NOT NULL,
	correo VARCHAR(150) NOT NULL,
	grupos_inscritos INT NOT NULL,

	FOREIGN KEY (id_carrera) REFERENCES Licenciaturas(id),
	FOREIGN KEY (correo) REFERENCES Usuarios(correo)


);

CREATE TABLE Grupos_inscritos_alumnos(
	matricula VARCHAR(12),
	id_curso1 INT,
	grupo1 INT,
	id_curso2 INT,
	grupo2 INT,
	id_curso3 INT,
	grupo3 INT,
	id_curso4 INT,
	grupo4 INT,
	id_curso5 INT,
	grupo5 INT,
	id_curso6 INT,
	grupo6 INT,
	id_curso7 INT,
	grupo7 INT,
	FOREIGN KEY (matricula) REFERENCES Alumnos_registrados(matricula)
);


CREATE TABLE Administradores(
	id INT PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
	correo VARCHAR(150) NOT NULL,
	FOREIGN KEY (correo) REFERENCES Usuarios(correo)

);



CREATE TABLE Grupos_registrados(
	id INT,
	grupo INT NOT NULL,
	alumnos_registrados INT NOT NULL,
	id_profesor INT NOT NULL,

	PRIMARY KEY (id, grupo), 
	FOREIGN KEY (id) REFERENCES Lista_cursos(id)

);

CREATE TABLE Profesores_registrados(
	id INT PRIMARY KEY,
	nombre VARCHAR(75) NOT NULL,
	apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
	cubiculo VARCHAR(50)NOT NULL,
	correo VARCHAR(150) NOT NULL,

	FOREIGN KEY (correo) REFERENCES Usuarios(correo)
);
/*
drop table Profesores_registrados
drop table Grupos_registrados
drop table Administradores
drop table Grupos_inscritos_alumnos
drop table Alumnos_registrados
drop table Usuarios
drop table Lista_cursos
drop table Licenciaturas
*/
 INSERT INTO Licenciaturas VALUES('APC');
 INSERT INTO Licenciaturas VALUES('CPAU');
 INSERT INTO Licenciaturas VALUES('CS');
 INSERT INTO Licenciaturas VALUES('CC');
 INSERT INTO Licenciaturas VALUES('CL');
 INSERT INTO Licenciaturas VALUES('FHI');
 INSERT INTO Licenciaturas VALUES('HSC');
 INSERT INTO Licenciaturas VALUES('D');
 INSERT INTO Licenciaturas VALUES('CA');
 INSERT INTO Licenciaturas VALUES('NS');
 INSERT INTO Licenciaturas VALUES('PS');
 INSERT INTO Licenciaturas VALUES('PCGR');
 INSERT INTO Licenciaturas VALUES('CG');
 INSERT INTO Licenciaturas VALUES('ISTU');
 INSERT INTO Licenciaturas VALUES('ISEI');
 INSERT INTO Licenciaturas VALUES('IS');
 INSERT INTO Licenciaturas VALUES('ISET');
 INSERT INTO Licenciaturas VALUES('ISE');
 INSERT INTO Licenciaturas VALUES('MM');