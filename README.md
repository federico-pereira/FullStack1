# FullStack1

Proyecto semestral de desarrollo Full Stack

## Descripción

Este repositorio contiene el proyecto semestral de la asignatura Full Stack. La aplicación permite gestionar un sistema de clases con contenido, alumno, profesores, entre otros, exponiendo una API REST en el backend.

## Tecnologías

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate, Lombok
* **Base de datos:** Oracle Bd (configurable en `application.properties`)
* **Frontend:** No existente
* **Gestión de dependencias:** Maven
* **Control de versiones:** Git

## Estructura de carpetas

```
📦FullStack1
 ┣ 📂proyect
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java/duoc/proyect
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┗ 📂test
 ┃ ┗ 📜pom.xml
 ┗ 📜README.md
```

## Requisitos previos

* Java 17 o superior
* Maven 3.6+

## Uso

* API REST disponible en: `http://localhost:8080/api/v1`
* application.properties
para cambiar la locacion de la walet --> spring.datasource.url=jdbc:oracle:thin:@fspdb_high?TNS_ADMIN=C:/
y las credenciales
spring.datasource.username=ADMIN
spring.datasource.password=FullStack-001

### Endpoints disponibles

A continuación, para cada recurso se muestra un ejemplo de:

* **GET** lista
* **GET** por id
* **DELETE** por id
* **POST**
* **PUT**

---

### Alumnos

* **GET** `/api/v1/alumnos`
* **GET** `/api/v1/alumnos/{id}`
* **DELETE** `/api/v1/alumnos/{id}`
* **POST** `/api/v1/alumnos`

```json
{
  "rut": "11.111.111-1",
  "name": "Juan",
  "lastName": "Pérez",
  "mail": "juan.perez@duoc.cl",
}
```

* **PUT** `/api/v1/alumnos/{id}`

````json
{
  "rut": "11.111.111-1",
  "name": "Juan Carlos",
  "lastName": "Pérez Gómez",
  "mail": "jcarlos.perez@duoc.cl",
}
````

---

### Contenidos

* **GET** `/api/v1/contenidos`
* **GET** `/api/v1/contenidos/{id}`
* **DELETE** `/api/v1/contenidos/{id}` (error si esta relacionado a un curso, eliminar de curso antes)
* **POST** `/api/v1/contenidos`

```json
{ "titulo": "Álgebra Lineal",
  "descripcion": "Fundamentos de espacios vectoriales"
}
```

* **PUT** `/api/v1/contenidos/{id}`

```json
{ "titulo": "Cálculo I", "descripcion": "Límites y derivadas" }
```

---

### CuponesDescuento

* **GET** `/api/v1/cupones`
* **GET** `/api/v1/cupones/{id}`
* **DELETE** `/api/v1/cupones/{id}`
* **POST** `/api/v1/cupones`

```json
{
 "descuento": 15
}
```

* **PUT** `/api/v1/cupones/{id}`

```json
{
 "descuento": 10
}
```
* **GET** `/api/v1/evaluaciones/{id}/cursos`
* **DELETE** `/api/v1/evaluaciones/{id}/cursos/{idCurso}`
* **POST** `/api/v1/evaluaciones/{id}/cursos/{idCurso}`
---

### Cursos

* **GET** `/api/v1/cursos`
* **GET** `/api/v1/cursos/{id}`
* **DELETE** `/api/v1/cursos/{id}` (si era relacionado a una evaluacion tira error)
* **POST** `/api/v1/cursos`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

* **PUT** `/api/v1/cursos/{id}`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```
#### /cursos/{idCurso}/alumnos
* **GET** `/api/v1/cursos/{idCurso}/alumnos`
* **DELETE** `/api/v1/cursos/{idCurso}/alumnos/{idAlumno}`
* **POST** `/api/v1/cursos/{idCurso}/alumnos`

```json
{
    "id":1
}
```

#### /cursos/{idCurso}/contenidos
* **GET** `/api/v1/cursos/{idCurso}/contenidos`
* **DELETE** `/api/v1/cursos/{idCurso}/contenidos/{idContenido}`
* **POST** `/api/v1/cursos/{idCurso}/contenidos`

```json
{
    "id":1
}
```
---

### Evaluaciones

* **GET** `/api/v1/evaluaciones`
* **GET** `/api/v1/evaluaciones/{id}`
* **DELETE** `/api/v1/evaluaciones/{id}` 
* **POST** `/api/v1/evaluaciones`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

* **PUT** `/api/v1/cursos/{id}`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

#### /evaluaciones/{idEvaluacion}/cursos
* **GET** `/api/v1/evaluaciones/{idEvaluacion}/cursos`
* **DELETE** `/api/v1/evaluaciones/{idEvaluacion}/cursos/{idCurso}`
* **POST** `/api/v1/evaluaciones/{idEvaluacion}/cursos`

```json
{
    "id":1
}
```
---

### Matriculas

* **GET** `/api/v1/matriculas`
* **GET** `/api/v1/matriculas/{id}`
* **DELETE** `/api/v1/matriculas/{id}`
* **POST** `/api/v1/matriculas`

```json
{
    "matricula": "test",
    "alumno": null,
    "cuponDescuento": null,
    "valor": 0.0
}
```

* **PUT** `/api/v1/matriculas/{id}`

```json
{
    "matricula": "test",
    "alumno": null,
    "cuponDescuento": null,
    "valor": 0.0
}
```
#### /matriculas/{idMatricula}/alumno
* **GET** `api/v1/matriculas/{idMatricula}/alumno`
* **DELETE** `api/v1/matriculas/{idMatricula}/alumno/{idAlumno}`
* **POST** `api/v1/matriculas/{idMatricula}/alumno`

```json
{
    "id":1
}
```
#### /matriculas/{idMatricula}/cupon
* **GET** `api/v1/matriculas/{idMatricula}/cupon`
* **DELETE** `api/v1/matriculas/{idMatricula}/cupon/{idCupon}`
* **POST** `api/v1/matriculas/{idMatricula}/cupon`

```json
{
    "id":1
}
```
---
### Profesores

* **GET** `/api/v1/profesores`
* **GET** `/api/v1/profesores/{id}`
* **DELETE** `/api/v1/profesores/{id}`
* **POST** `/api/v1/profesores`

```json
{
        "rut": "44.444.444-3",
        "name": "Manuel",
        "lastName": "jose",
        "mail": "Manuel.jose@uch.cl"
}
```
* **PUT** `/api/v1/profesores/{id}`

```json
{
        "rut": "44.444.444-6",
        "name": "Fede",
        "lastName": "Pereira",
        "mail": "Fede.Pereira@uch.cl"
}
```
---
### Soportes

* **GET** `/api/v1/soportes`
* **GET** `/api/v1/soportes/{id}`
* **DELETE** `/api/v1/soportes/{id}`
* **POST** `/api/v1/soportes`

```json
{
        "rut": "test",
        "name": "test",
        "lastName": "test",
        "mail": "test",
        "departamento": "test"
}
```
* **PUT** `/api/v1/soportes/{id}`

```json
{
        "rut": "test",
        "name": "test",
        "lastName": "test",
        "mail": "test",
        "departamento": "test"
}
```
#### /soportes/{idSoporte}/tickets
* **GET** `api/v1/soportes/{idSoporte}/tickets`
* **DELETE** `api/v1/soportes//{idSoporte}/tickets/{idTicket}`
* **POST** `api/v1/soportes/{idSoporte}/tickets`

```json
{
    "id":1
}
```

---
### Tickets soporte

* **GET** `/api/v1/tickets`
* **GET** `/api/v1/tickets/{id}`
* **DELETE** `/api/v1/tickets/{id}`
* **POST** `/api/v1/tickets`

```json
{
        "tema": "error en log in",
        "descripcion": "se me olvido la clave"
}
```

* **PUT** `/api/v1/tickets/{id}`

```json
{
        "tema": "test",
        "descripcion": "test"
}
```
---

## Contribuciones

Se aceptan pull requests:

1. Hacer fork del repositorio
2. Crear nueva rama:
3. Realizar cambios y commits claros
4. Abrir Pull Request describiendo la funcionalidad o corrección

## Contacto

Federico Pereira – [federico.pereira@duoc.cl](mailto:federico.pereira@duoc.cl)
Martin Gonzalez
