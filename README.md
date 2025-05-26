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

* **GET** `/api/alumnos`
* **GET** `/api/alumnos/{id}`
* **DELETE** `/api/alumnos/{id}`
* **POST** `/api/alumnos`

```json
{
  "rut": "11.111.111-1",
  "name": "Juan",
  "lastName": "Pérez",
  "mail": "juan.perez@duoc.cl",
}
```

* **PUT** `/api/profesores/{id}`

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

* **GET** `/api/contenidos`
* **GET** `/api/contenidos/{id}`
* **DELETE** `/api/contenidos/{id}` (error si esta relacionado a un curso, eliminar de curso antes)
* **POST** `/api/contenidos`

```json
{ "titulo": "Álgebra Lineal",
  "descripcion": "Fundamentos de espacios vectoriales"
}
```

* **PUT** `/api/contenidos/{id}`

```json
{ "titulo": "Cálculo I", "descripcion": "Límites y derivadas" }
```

---

### CuponesDescuento

* **GET** `/api/cupones`
* **GET** `/api/cupones/{id}`
* **DELETE** `/api/cupones/{id}`
* **POST** `/api/cupones`

```json
{
 "descuento": 15
}
```

* **PUT** `/api/cupones/{id}`

```json
{
 "descuento": 10
}
```
* **GET** `/api/evaluaciones/{id}/cursos`
* **DELETE** `/api/evaluaciones/{id}/cursos/{idCurso}`
* **POST** `/api/evaluaciones/{id}/cursos/{idCurso}`
---

### Cursos

* **GET** `/api/cursos`
* **GET** `/api/cursos/{id}`
* **DELETE** `/api/cursos/{id}` (si era relacionado a una evaluacion tira error)
* **POST** `/api/cursos`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

* **PUT** `/api/cursos/{id}`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```
#### /cursos/{idCurso}/alumnos
* **GET** `/api/cursos/{idCurso}/alumnos`
* **DELETE** `/api/cursos/{idCurso}/alumnos/{idAlumno}`
* **POST** `/api/cursos/{idCurso}/alumnos`

```json
{
    "id":1
}
```

#### /cursos/{idCurso}/contenidos
* **GET** `/api/cursos/{idCurso}/contenidos`
* **DELETE** `/api/cursos/{idCurso}/contenidos/{idContenido}`
* **POST** `/api/cursos/{idCurso}/contenidos`

```json
{
    "id":1
}
```
---

### Evaluaciones

* **GET** `/api/evaluaciones`
* **GET** `/api/evaluaciones/{id}`
* **DELETE** `/api/evaluaciones/{id}` 
* **POST** `/api/evaluaciones`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

* **PUT** `/api/cursos/{id}`

```json
{
    "name": null,
    "profesor": {
        "id":3
    }
}
```

#### /evaluaciones/{idEvaluacion}/cursos
* **GET** `/api/evaluaciones/{idEvaluacion}/cursos`
* **DELETE** `/api/evaluaciones/{idEvaluacion}/cursos/{idCurso}`
* **POST** `/api/evaluaciones/{idEvaluacion}/cursos`

```json
{
    "id":1
}
```
---

### Matriculas

* **GET** `/api/matriculas`
* **GET** `/api/matriculas/{id}`
* **DELETE** `/api/matriculas/{id}`
* **POST** `/api/matriculas`

```json
{
    "matricula": "test",
    "alumno": null,
    "cuponDescuento": null,
    "valor": 0.0
}
```

* **PUT** `/api/matriculas/{id}`

```json
{
    "matricula": "test",
    "alumno": null,
    "cuponDescuento": null,
    "valor": 0.0
}
```
#### /matriculas/{idMatricula}/alumno
* **GET** `/matriculas/{idMatricula}/alumno`
* **DELETE** `/matriculas/{idMatricula}/alumno/{idAlumno}`
* **POST** `/matriculas/{idMatricula}/alumno`

```json
{
    "id":1
}
```
#### /matriculas/{idMatricula}/cupon
* **GET** `/matriculas/{idMatricula}/cupon`
* **DELETE** `/matriculas/{idMatricula}/cupon/{idCupon}`
* **POST** `/matriculas/{idMatricula}/cupon`

```json
{
    "id":1
}
```
---
### Profesores

* **GET** `/api/profesores`
* **GET** `/api/profesores/{id}`
* **DELETE** `/api/profesores/{id}`
* **POST** `/api/profesores`

```json
{
        "rut": "44.444.444-3",
        "name": "Manuel",
        "lastName": "jose",
        "mail": "Manuel.jose@uch.cl"
}
```
* **PUT** `/api/profesores/{id}`

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

* **GET** `/api/soportes`
* **GET** `/api/soportes/{id}`
* **DELETE** `/api/soportes/{id}`
* **POST** `/api/soportes`

```json
{
        "rut": "test",
        "name": "test",
        "lastName": "test",
        "mail": "test",
        "departamento": "test"
}
```
* **PUT** `/api/soportes/{id}`

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
* **GET** `/soportes/{idSoporte}/tickets`
* **DELETE** `/soportes//{idSoporte}/tickets/{idTicket}`
* **POST** `/soportes/{idSoporte}/tickets`

```json
{
    "id":1
}
```

---
### Tickets soporte

* **GET** `/api/tickets`
* **GET** `/api/tickets/{id}`
* **DELETE** `/api/tickets/{id}`
* **POST** `/api/tickets`

```json
{
        "tema": "error en log in",
        "descripcion": "se me olvido la clave"
}
```

* **PUT** `/api/tickets/{id}`

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
