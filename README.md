-Federico Pereira
-Martín gonzález

¿Para qué sirve el código?

El proyecto es un sistema académico desarrollado como proyecto semestral full stack (semestre 3). Gestiona entidades típicas de un entorno educativo: alumnos, profesores, cursos y contenidos. Permite crear, consultar, actualizar y eliminar estos elementos, y manejar la relación entre ellos (por ejemplo, asignar alumnos a cursos, agregar contenidos a cursos, etc.).

Tecnologías utilizadas

Java (100%): Lenguaje principal del backend.
Spring Boot: Framework para crear aplicaciones web y APIs REST de forma rápida y estructurada.
Spring Data JPA: Para la gestión y persistencia de datos en base de datos mediante entidades y repositorios.
Lombok: Reduce la escritura de código repetitivo (getters/setters, constructores, etc.).
Maven: Para la gestión de dependencias y construcción del proyecto.
Base de datos relacional: El uso de JPA y anotaciones @Entity indica que se ocupa una base de datos SQL (Oracle).

¿Cómo se conecta y ocupa la base de datos?

Todas las entidades principales (Alumno, Profesor, Curso, Contenido) están anotadas con @Entity y usan JPA.
Las relaciones están bien modeladas: por ejemplo, un curso puede tener muchos alumnos y contenidos; los alumnos pueden estar en varios cursos.
Se usan repositorios de Spring Data para interactuar con la base de datos (por ejemplo, CursoRepository, AlumnoRepository).
La configuración específica de conexión (usuario, contraseña, URL de la base de datos) iría en el archivo de configuración de Spring Boot 
(application.properties o application.yml), aunque no se muestra en los fragmentos obtenidos.

¿Cómo se usan las APIs?

El sistema expone una serie de APIs REST para interactuar con los datos. Cada entidad principal tiene un controlador dedicado:

AlumnoController (/api/v1/alumnos)

GET /: lista todos los alumnos.
GET /{id}: obtiene un alumno por id.
POST /: crea un alumno.
PUT /{id}: actualiza un alumno.
DELETE /{id}: elimina un alumno.
ProfesorController (/api/v1/profesores)

GET /: lista todos los profesores.
GET /{id}: obtiene un profesor por id.
POST /: crea un profesor.
PUT /{id}: actualiza un profesor.
DELETE /{id}: elimina un profesor.
CursoController (/api/v1/cursos)

GET /: lista todos los cursos.
GET /{id}: obtiene un curso por id.
POST /: crea un curso.
PUT /{id}: actualiza un curso.
DELETE /{id}: elimina un curso.
Maneja asociaciones con alumnos y contenidos, por ejemplo:
GET /{idCurso}/alumnos: lista los alumnos de un curso.
POST /{idCurso}/alumnos: agrega un alumno a un curso.
DELETE /{idCurso}/alumnos/{id}: elimina un alumno de un curso.
GET/POST/DELETE para contenidos asociados al curso.
ContenidoController (/api/v1/contenidos)

CRUD sobre los contenidos que se pueden asociar a los cursos.
La comunicación se hace por HTTP (usualmente con JSON).

Explicación básica del código

Los modelos (model/) definen las entidades de negocio y sus relaciones con la base de datos.
Los servicios (service/) contienen la lógica de negocio y se comunican con los repositorios.
Los controladores (controller/) exponen las APIs REST y gestionan las solicitudes HTTP.
El arranque del proyecto está en ProyectApplication.java usando Spring Boot.
Ejemplo de relación:
Un Curso tiene una lista de Alumnos y Contenidos. Los métodos addAlumno, removeAlumno, addContenido, removeContenido sincronizan ambas partes de la relación.
