CREATE TABLE Licenciaturas(
	id INT PRIMARY KEY IDENTITY(1,1),
	nombre VARCHAR(75) NOT NULL
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
CREATE TABLE Profesores_registrados(
	id INT PRIMARY KEY,
	nombre VARCHAR(75) NOT NULL,
	apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
	cubiculo VARCHAR(50)NOT NULL,
	correo VARCHAR(150) NOT NULL,

	FOREIGN KEY (correo) REFERENCES Usuarios(correo) ON UPDATE CASCADE
);	

CREATE TABLE Alumnos_registrados(
	matricula VARCHAR(12) PRIMARY KEY,
	nombre VARCHAR(75) NOT NULL,
	apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
	id_carrera INT NOT NULL,
	correo VARCHAR(150) NOT NULL,
	grupos_inscritos INT NOT NULL,

	FOREIGN KEY (id_carrera) REFERENCES Licenciaturas(id) ON UPDATE CASCADE,
	FOREIGN KEY (correo) REFERENCES Usuarios(correo) ON UPDATE CASCADE


);
CREATE TABLE Grupos_registrados(
	id INT,
	grupo INT,
	alumnos_registrados INT NOT NULL,
	id_profesor INT NOT NULL,

	PRIMARY KEY (id, grupo), 
	FOREIGN KEY (id) REFERENCES Lista_cursos(id) ON UPDATE CASCADE,
	FOREIGN KEY (id_profesor) REFERENCES Profesores_registrados(id) ON UPDATE CASCADE

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
	FOREIGN KEY (matricula) REFERENCES Alumnos_registrados(matricula) ON UPDATE CASCADE,
);







-- Insertamos licenciaturas
INSERT INTO Licenciaturas VALUES('Arte y Patrimonio Cultural');
INSERT INTO Licenciaturas VALUES('Ciencia Politica y Administración Urbana');
INSERT INTO Licenciaturas VALUES('Ciencias Sociales');
INSERT INTO Licenciaturas VALUES('Comunicación y Cultura');
INSERT INTO Licenciaturas VALUES('Creación Literaria');
INSERT INTO Licenciaturas VALUES('Filosofía y Historia de las Ideas');
INSERT INTO Licenciaturas VALUES('Historia y Sociedad Contemporanea');
INSERT INTO Licenciaturas VALUES('Derecho');
INSERT INTO Licenciaturas VALUES('Ciencias Ambientales');
INSERT INTO Licenciaturas VALUES('Nutrición y Salud');
INSERT INTO Licenciaturas VALUES('Promoción de la Salud');
INSERT INTO Licenciaturas VALUES('Protección Civil y Gestión de Riesgos');
INSERT INTO Licenciaturas VALUES('Ciencias Genómicas');
INSERT INTO Licenciaturas VALUES('Ing. en Sistemas de Transporte Urbano');
INSERT INTO Licenciaturas VALUES('Ing. en Sistemas Electronicos Industriales');
INSERT INTO Licenciaturas VALUES('Ing. de Software');
INSERT INTO Licenciaturas VALUES('Ing. en sistemas Electronicos y de Telecomunicaciones');
INSERT INTO Licenciaturas VALUES('Ing. en Sistemas Energéticos');
INSERT INTO Licenciaturas VALUES('Modelación Matemática');

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
INSERT INTO Lista_cursos VALUES('Arquitectura de computadoras' , 'CCyT', 'SUPERIOR');
INSERT INTO Lista_cursos VALUES('Teoría politica I' , 'CCS', 'BASICO');
INSERT INTO Lista_cursos VALUES('Teoría politica II' , 'CCS', 'BASICO');
INSERT INTO Lista_cursos VALUES('Teoría politica III' , 'CCS', 'BASICO');

-- insertamos correos
INSERT INTO Usuarios VALUES('adriana.hernandez10@profesor.uacm.edu.mx','rivera10',26,'profesor');
INSERT INTO Usuarios VALUES('ernesto.colavita7@profesor.uacm.edu.mx','hernandez7',13,'profesor');
INSERT INTO Usuarios VALUES('hildeberto.tovar11@profesor.uacm.edu.mx','garcia11',54,'profesor');
INSERT INTO Usuarios VALUES('horacio.escalona5@profesor.uacm.edu.mx','buendia5',44,'profesor');
INSERT INTO Usuarios VALUES('jesus.carrillo9@profesor.uacm.edu.mx','pacheco9',63,'profesor');
INSERT INTO Usuarios VALUES('jorge.wals3@profesor.uacm.edu.mx','selvas3',67,'profesor');
INSERT INTO Usuarios VALUES('jose.quiroz6@profesor.uacm.edu.mx','fabian6',49,'profesor');
INSERT INTO Usuarios VALUES('marco.becerril13@profesor.uacm.edu.mx','palma13',1,'profesor');
INSERT INTO Usuarios VALUES('marco.noguez8@profesor.uacm.edu.mx','cordoba8',72,'profesor');
INSERT INTO Usuarios VALUES('omar.nieto1@profesor.uacm.edu.mx','crisostomo1',8,'profesor');
INSERT INTO Usuarios VALUES('sergio.ortiz4@profesor.uacm.edu.mx','leroux4',25,'profesor');
INSERT INTO Usuarios VALUES('silvia.andrade2@profesor.uacm.edu.mx','rodriguez2',97,'profesor');
INSERT INTO Usuarios VALUES('brian.escalona210034123@alumnos.uacm.edu.mx','maldonado210034123',17,'alumno');
INSERT INTO Usuarios VALUES('diana.ramirez230032340@alumnos.uacm.edu.mx','chavez230032340',41,'alumno');
INSERT INTO Usuarios VALUES('fernando.arroyo180030088@alumnos.uacm.edu.mx','velasco180030088',64,'alumno');
INSERT INTO Usuarios VALUES('hugo.ramirez150030456@alumnos.uacm.edu.mx','castañeda150030456',53,'alumno');
INSERT INTO Usuarios VALUES('leonardo.rodriguez200030699@alumnos.uacm.edu.mx','rodriguez200030699',46,'alumno');
INSERT INTO Usuarios VALUES('leslie.rodriguez230030789@alumnos.uacm.edu.mx','rodriguez230030789',44,'alumno');
INSERT INTO Usuarios VALUES('maria.molina220036745@alumnos.uacm.edu.mx','vivas220036745',5,'alumno');
INSERT INTO Usuarios VALUES('maria.rodriguez240030001@alumnos.uacm.edu.mx','rodriguez240030001',6,'alumno');
INSERT INTO Usuarios VALUES('roberto.rodriguez170010456@alumnos.uacm.edu.mx','alvarez170010456',75,'alumno');
INSERT INTO Usuarios VALUES('wendolyn.medina200030988@alumnos.uacm.edu.mx','chavez200030988',64,'alumno');

--insertamos alumnos
INSERT INTO Alumnos_registrados VALUES('15-003-0456','Hugo Enrique','Ramirez','Castañeda',5,'hugo.ramirez150030456@alumnos.uacm.edu.mx',0);	
INSERT INTO Alumnos_registrados VALUES('17-001-0456','Roberto','Rodriguez','Alvarez',12,'roberto.rodriguez170010456@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('18-003-0088','Fernando Ocftavio','Arroyo','Velasco',16,'fernando.arroyo180030088@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('20-003-0699','Leonardo','Rodriguez','Rodriguez',16,'leonardo.rodriguez200030699@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('20-003-0988','Wendolyn','Medina','Chavez',16,'wendolyn.medina200030988@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('21-003-4123','Brian Miguel','Escalona','Maldonado',16,'brian.escalona210034123@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('22-003-6745','Maria Eugenia','Molina','Vivas',5,'maria.molina220036745@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('23-003-0789','Leslie','Rodriguez','Rodriguez',11,'leslie.rodriguez230030789@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('23-003-2340','Diana Jasabel','Ramirez','Chavez',4,'diana.ramirez230032340@alumnos.uacm.edu.mx',0);
INSERT INTO Alumnos_registrados VALUES('24-003-0001','Maria Jose','Rodriguez','Rodriguez',3,'maria.rodriguez240030001@alumnos.uacm.edu.mx',0);

--insertamos profesores
INSERT INTO Profesores_registrados VALUES(1,'Omar','Nieto','Crisostomo','E-027','omar.nieto1@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(2,'Silvia Alejandra','Andrade','Rodriguez','E-277','silvia.andrade2@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(3,'Jorge Enrique','Wals','Selvas','D-001','jorge.wals3@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(4,'Sergio','Ortiz','Leroux','E-134','sergio.ortiz4@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(5,'Horacio','Escalona','Buendia','E-012','horacio.escalona5@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(6,'Jose Luis','Quiroz','Fabian','E-123','jose.quiroz6@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(7,'Ernesto','Colavita','Hernandez','A-402','ernesto.colavita7@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(8,'Marco Antonio','Noguez','Cordoba','A-402','marco.noguez8@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(9,'Jesus','Carrillo','Pacheco','B-401','jesus.carrillo9@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(10,'Adriana','Hernandez','Rivera','D-234','adriana.hernandez10@profesor.uacm.edu.mx');
INSERT INTO Profesores_registrados VALUES(11,'Hildeberto','Tovar','Garcia','E-120','hildeberto.tovar11@profesor.uacm.edu.mx');


--insertamos grupos (con profesores ya dados de alta)
INSERT INTO Grupos_registrados VALUES(2, 2, 0, 3);
INSERT INTO Grupos_registrados VALUES(2, 113, 0, 9);
INSERT INTO Grupos_registrados VALUES(3, 160, 0, 7);
INSERT INTO Grupos_registrados VALUES(5, 110, 0, 8);
INSERT INTO Grupos_registrados VALUES(5, 214, 0, 8);
INSERT INTO Grupos_registrados VALUES(7, 203, 0, 9);
INSERT INTO Grupos_registrados VALUES(8, 210, 0, 11);
INSERT INTO Grupos_registrados VALUES(9, 103, 0, 6);
INSERT INTO Grupos_registrados VALUES(10, 301, 0, 1);
INSERT INTO Grupos_registrados VALUES(11, 405, 0, 5);
INSERT INTO Grupos_registrados VALUES(12, 320, 0, 2);
INSERT INTO Grupos_registrados VALUES(12, 456, 0, 5);
INSERT INTO Grupos_registrados VALUES(13, 500, 0, 2);
INSERT INTO Grupos_registrados VALUES(14, 308, 0, 1);
INSERT INTO Grupos_registrados VALUES(15, 115, 0, 6);
INSERT INTO Grupos_registrados VALUES(16, 298, 0, 3);
INSERT INTO Grupos_registrados VALUES(17, 430, 0, 3);
INSERT INTO Grupos_registrados VALUES(18, 201, 0, 4);
INSERT INTO Grupos_registrados VALUES(19, 102, 0, 4);

-- creamos las tablas correspondientes a los grupos

CREATE TABLE G2_2(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G2_113(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G3_160(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G5_110(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G5_214(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G7_203(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G8_210(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G9_103(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G10_301(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G11_405(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G12_320(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G12_456(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G13_500(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G14_308(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G15_115(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G16_298(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G17_430(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G18_201(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
CREATE TABLE G19_102(
	matricula VARCHAR(12),
	proyectos DECIMAL(10,2) NOT NULL,
	tareas DECIMAL(10,2) NOT NULL,
	examenes DECIMAL (10,2) NOT NULL,
	promedio DECIMAL (10,2) NOT NULL,

	FOREIGN KEY(matricula) REFERENCES Alumnos_registrados(matricula)
);
INSERT INTO Usuarios VALUES ('admin.2001@administrador.edu.mx ','12345',1,'administrador');

/*

DROP TABLE Grupos_inscritos_alumnos
DROP TABLE Grupos_registrados
DROP TABLE Alumnos_registrados
DROP TABLE Profesores_registrados
DROP TABLE Usuarios
DROP TABLE Lista_cursos
DROP TABLE Licenciaturas
*/