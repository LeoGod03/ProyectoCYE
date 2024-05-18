CREATE TABLE Licenciaturas(
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre VARCHAR(50) NOT NULL
);
	
CREATE TABLE Lista_cursos(
	id INT PRIMARY KEY IDENTITY(1,1),
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
	FOREIGN KEY (id) REFERENCES Lista_cursos(id),
	FOREIGN KEY (id_profesor) REFERENCES Profesores_registrados(id)

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
-- Insertamos licenciaturas
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

 -- insertamos cursos

INSERT INTO Lista_cursos VALUES('Algebra y Geometria Analitica' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Algebra Lineal' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Termodinamica y Fluidos' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Mecanica I' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Mecanica II' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Calculo Diferencial' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Calculo Integral' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Introducción a la programación' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Introducción a la ingeniería de software' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Programación orientada a objetos' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Estructura de datos' , 'CCyT', 'BASICO');
INSERT INTO Lista_cursos VALUES('Bases de datos' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Análisis de requisitos' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Contrucción y evolución del software' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Análisis de algoritmos' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Teoría de la computación' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Teoría politica I' , 'CCS', 'BASICO');
INSERT INTO Lista_cursos VALUES('Teoría politica II' , 'CCS', 'BASICO');
INSERT INTO Lista_cursos VALUES('Teoría politica III' , 'CCS', 'BASICO');

--insertamos grupos (con profesores ya dados de alta)

INSERT INTO Grupos_registrados VALUES(1, 101, 0, 2);
INSERT INTO Grupos_registrados VALUES(1, 203, 0, 5);
INSERT INTO Grupos_registrados VALUES(2, 105, 0, 5);
INSERT INTO Grupos_registrados VALUES(3, 105, 0, 6);
INSERT INTO Grupos_registrados VALUES(3, 210, 0, 5);
INSERT INTO Grupos_registrados VALUES(4, 102, 0, 3);
INSERT INTO Grupos_registrados VALUES(4, 201, 0, 4);
INSERT INTO Grupos_registrados VALUES(4, 301, 0, 1);
INSERT INTO Grupos_registrados VALUES(5, 107, 0, 1);
INSERT INTO Grupos_registrados VALUES(5, 221, 0, 4);
INSERT INTO Grupos_registrados VALUES(6, 110, 0, 2);
INSERT INTO Grupos_registrados VALUES(6, 112, 0, 6);
INSERT INTO Grupos_registrados VALUES(7, 103, 0, 1);
INSERT INTO Grupos_registrados VALUES(7, 106, 0, 6);
INSERT INTO Grupos_registrados VALUES(13, 401, 0, 2);

CREATE TABLE G1_101(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G1_203(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G2_105(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G3_105(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G3_210(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G4_102(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G4_201(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G4_301(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G5_107(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G5_221(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G6_110(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G6_112(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G7_103(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G7_106(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);

CREATE TABLE G13_401(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);