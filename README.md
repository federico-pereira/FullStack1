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
* **DELETE** `/api/contenidos/{id}` (error si esta relacionado a un curso)
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

### Soportes

* **GET** `/api/soportes`
* **GET** `/api/soportes/{id}`
* **DELETE** `/api/soportes/{id}`
* **POST** `/api/soportes`

```json
{ "rut": "22.222.222-2", "name": "Luis", "lastName": "Ramírez", "mail": "luis@example.com", "departamento": "TI" }
```

* **PUT** `/api/soportes/{id}`

```json
{ "departamento": "Atención al Cliente" }
```

---

### Tickets

* **GET** `/api/tickets`
* **GET** `/api/tickets/{id}`
* **DELETE** `/api/tickets/{id}`
* **POST** `/api/tickets`

```json
{ "asunto": "Error 500", "descripcion": "Falla en servidor", "prioridad": "ALTA" }
```

* **PUT** `/api/tickets/{id}`

```json
{ "prioridad": "MEDIA", "estado": "EN_PROGRESO" }
```

---

### Alumnos

* **GET** `/api/alumnos`
* **GET** `/api/alumnos/{id}`
* **DELETE** `/api/alumnos/{id}`
* **POST** `/api/alumnos`

```json
{ "nombre": "María", "curso": "Ingeniería" }
```

* **PUT** `/api/alumnos/{id}`

```json
{ "curso": "Arquitectura" }
```

---

### Matrículas

* **GET** `/api/matriculas`
* **GET** `/api/matriculas/{id}`
* **DELETE** `/api/matriculas/{id}`
* **POST** `/api/matriculas`

```json
{ "matricula": "2025-01" }
```

* **PUT** `/api/matriculas/{id}`

```json
{ "matricula": "2025-02" }
```

---

### Cupones de descuento

* **GET** `/api/cupones`
* **GET** `/api/cupones/{id}`
* **DELETE** `/api/cupones/{id}`
* **POST** `/api/cupones`

```json
{ "codigo": "PROMO10", "descuento": 10 }
```

* **PUT** `/api/cupones/{id}`

```json
{ "descuento": 15 }
```

## Contribuciones

Se aceptan pull requests:

1. Hacer fork del repositorio
2. Crear nueva rama: `git checkout -b feature/nombre-feature`
3. Realizar cambios y commits claros
4. Abrir Pull Request describiendo la funcionalidad o corrección

## Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](LICENSE) para más información.

## Contacto

Federico Pereira – [federico.pereira@duoc.cl](mailto:federico.pereira@duoc.cl)
Repositorio: [https://github.com/federico-pereira/FullStack1](https://github.com/federico-pereira/FullStack1)
