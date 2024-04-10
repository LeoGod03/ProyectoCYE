Create table Alumnos_registrados(
	matricula varchar(12) primary key,
	nombre varchar(50) not null,
	apellido_paterno varchar(50) not null,
	apellido_materno varchar(50) not null,
	id_carrera int not null,
	edad int not null,
	correo varchar(50) not null
);

--drop table Cursos_registros
Create table Cursos_inscritos_alumnos(
	matricula varchar(12),
	id_curso1 int,
	grupo1 int,
	id_curso2 int,
	grupo2 int,
	id_curso3 int,
	grupo3 int,
	id_curso4 int,
	grupo4 int,
	id_curso5 int,
	grupo5 int,
	id_curso6 int,
	grupo6 int,
	id_curso7 int,
	grupo7 int,

);

create table Usuarios(
	correo varchar(50) primary key,
	contraseña varchar(50) not null,
	llave int not null,
	rol varchar(50) not null
);


create table Administradores(
	id int primary key,
	nombre varchar(50) not null,
	apellido_paterno varchar(50) not null,
	apellido_materno varchar(50) not null,
	edad int not null,
	correo varchar(50) not null

);

--drop table Cursos_registrados
create table Cursos_registrados(
	id int not null,
	grupo int not null,
	alumnos_registrados int not null,
	id_profesor int not null
	Primary key(id,grupo)
);

create table Lista_cursos(
	id int primary key,
	nombre varchar(100) not null,
	colegio varchar(10) not null,
	ciclo varchar(20) not null
);