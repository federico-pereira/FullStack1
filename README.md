# FullStack1

Sistema académico full stack desarrollado como proyecto semestral (semestre 3) que permite gestionar entidades típicas de un entorno educativo: alumnos, profesores, cursos y contenidos. Permite realizar operaciones CRUD y gestionar las relaciones entre estas entidades.

## Tabla de contenidos

* [About](#about)
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [Project Structure](#project-structure)
* [API Reference](#api-reference)
* [Contributing](#contributing)
* [Authors](#authors)
* [License](#license)

## About

FullStack1 es un sistema académico que expone una API REST para gestionar alumnos, profesores, cursos y contenidos, incluyendo la asignación de alumnos a cursos y la asociación de contenidos con cursos. ([github.com](https://github.com/federico-pereira/FullStack1/tree/martin-branch))

## Technologies

* **Java 11+**: Lenguaje principal del backend en Spring Boot. 
* **Spring Boot**: Framework para crear aplicaciones web y APIs REST. 
* **Spring Data JPA**: Gestión y persistencia de datos en base de datos SQL mediante entidades y repositorios. 
* **Lombok**: Reducción de código repetitivo (getters/setters, constructores). 
* **Maven**: Gestión de dependencias y construcción del proyecto. 
* **Base de datos relacional (Oracle)** utilizada a través de JPA. 

## Prerequisites

* Java JDK 11 o superior
* Maven 3.6+
* Acceso a una base de datos Oracle (u otra base de datos compatible con JPA)

## Installation

1. Clona el repositorio:

   ```bash
   git clone https://github.com/federico-pereira/FullStack1.git
   cd FullStack1
   ```
2. Configura la conexión a la base de datos en `src/main/resources/application.properties` (ver sección **Configuration**).
3. Compila e instala las dependencias:

   ```bash
   mvn clean install
   ```
4. Ejecuta la aplicación:

   ```bash
   mvn spring-boot:run
   ```

La API quedará disponible en `http://localhost:8080/api/v1` por defecto.

## Configuration

Edita el archivo `src/main/resources/application.properties` y define las siguientes propiedades:

```properties
spring.datasource.url=jdbc:oracle:thin:@<HOST>:<PORT>:<SID>
spring.datasource.username=<USUARIO>
spring.datasource.password=<PASSWORD>
spring.jpa.hibernate.ddl-auto=update
```

## Project Structure

```
FullStack1/
├── proyect/                    # Código fuente del backend
│   ├── src/main/java/...       # Paquetes: model, repository, service, controller
│   │   ├── model/              # Entidades JPA (Alumno, Profesor, Curso, Contenido)
│   │   ├── repository/         # Interfaces Spring Data JPA
│   │   ├── service/            # Lógica de negocio
│   │   ├── controller/         # Controladores REST (API endpoints)
│   │   └── ProyectApplication.java  # Clase principal de arranque
│   └── pom.xml                 # Configuración de Maven
└── README.md                   # Documentación del proyecto
```

## API Reference

### AlumnoController (`/api/v1/alumnos`)

* `GET /` : Lista todos los alumnos
* `GET /{id}` : Obtiene un alumno por ID
* `POST /` : Crea un nuevo alumno
* `PUT /{id}` : Actualiza un alumno existente
* `DELETE /{id}` : Elimina un alumno

### ProfesorController (`/api/v1/profesores`)

* `GET /` : Lista todos los profesores
* `GET /{id}` : Obtiene un profesor por ID
* `POST /` : Crea un nuevo profesor
* `PUT /{id}` : Actualiza un profesor existente
* `DELETE /{id}` : Elimina un profesor

### CursoController (`/api/v1/cursos`)

* `GET /` : Lista todos los cursos
* `GET /{id}` : Obtiene un curso por ID
* `POST /` : Crea un nuevo curso
* `PUT /{id}` : Actualiza un curso existente
* `DELETE /{id}` : Elimina un curso
* `GET /{idCurso}/alumnos` : Lista los alumnos de un curso
* `POST /{idCurso}/alumnos` : Agrega un alumno a un curso
* `DELETE /{idCurso}/alumnos/{id}` : Elimina un alumno de un curso
* `GET /{idCurso}/contenidos` : Lista los contenidos de un curso
* `POST /{idCurso}/contenidos` : Agrega contenido a un curso
* `DELETE /{idCurso}/contenidos/{id}` : Elimina contenido de un curso 

### ContenidoController (`/api/v1/contenidos`)

* `GET /` : Lista todos los contenidos
* `GET /{id}` : Obtiene un contenido por ID
* `POST /` : Crea un nuevo contenido
* `PUT /{id}` : Actualiza un contenido existente
* `DELETE /{id}` : Elimina un contenido

## Contributing

1. Haz un *fork* del repositorio.
2. Crea una rama con el nombre `feature/tu-mejora`.
3. Realiza los cambios y haz *commit* con mensajes descriptivos.
4. Envía un *pull request* describiendo tus modificaciones.

## Authors

* Federico Pereira
* Martín González 

## License

Este proyecto está bajo la licencia MIT.
