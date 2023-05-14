#Crear base de datos
CREATE DATABASE hotel_alura;

#Seleccionar la base de datos
USE hotel_alura;

#Crear la tabla para las reservas
CREATE TABLE reservas (
id INT NOT NULL AUTO_INCREMENT,
fecha_entrada DATE NOT NULL,
fecha_salida DATE NOT NULL,
valor VARCHAR(50),
forma_pago VARCHAR(59) NOT NULL,
PRIMARY KEY (id)
);

#Crear la tabla para los huespedes
CREATE TABLE huespedes (
id INT NOT NULL AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
fecha_nacimiento DATE NOT NULL,
nacionalidad VARCHAR(50) NOT NULL,
telefono VARCHAR(50) NOT NULL,
id_reserva INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (id_reserva) REFERENCES reservas(id)
);

#Crear la tabla para los usuarios y asi poder entrar al programa
CREATE TABLE usuarios (
nombre VARCHAR(50),
contraseña VARCHAR(50)
);

#Para insertar el usuario y contraseña de cada quien
INSERT INTO usuarios (nombre, contraseña)
VALUES ("admin", "admin");

INSERT INTO usuarios (nombre, contraseña)
VALUES ("dd", "12345678");

#Seleccionar cada tabla
SELECT * FROM usuarios;

SELECT * FROM huespedes;

SELECT * FROM reservas;