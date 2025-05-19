# FullStack1

Proyecto semestral de desarrollo Full Stack – Semestre 3

## Descripción

Este repositorio contiene el proyecto semestral de la asignatura Full Stack del tercer semestre. La aplicación permite gestionar usuarios y tickets de soporte, exponiendo una API REST en el backend y una interfaz web en el frontend.

## Tecnologías

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate, Lombok
* **Base de datos:** Oracle Bd (configurable en `application.properties`)
* **Frontend:** \[Indicar framework/librería: React
* **Gestión de dependencias:** Maven
* **Control de versiones:** Git

## Estructura de carpetas

```
📦FullStack1
 ┣ 📂proyect
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java/duoc/proyect
 ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┗ 📂test
 ┃ ┗ 📜pom.xml
 ┗ 📜README.md
```

## Requisitos previos

* Java 17 o superior
* Maven 3.6+
* (Opcional) Docker y Docker Compose para contenerizar la aplicación

## Instalación y ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/federico-pereira/FullStack1.git
   ```
2. Ubicarse en la carpeta del backend:

   ```bash
   cd FullStack1/proyect
   ```
3. Construir y arrancar el servidor Spring Boot:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. (Si dispone de frontend separado) Entrar a la carpeta `frontend`, instalar dependencias y levantar el servidor:

   ```bash
   cd frontend
   npm install
   npm start
   ```

## Uso

* API REST disponible en: `http://localhost:8080/api`
* Interfaz web en: `http://localhost:4200`

### Endpoints disponibles

A continuación, para cada recurso se muestra un ejemplo de:

* **GET** lista
* **GET** por id
* **DELETE** por id
* **POST**
* **PUT**

---

### Profesores

* **GET** `/api/profesores`
* **GET** `/api/profesores/{id}`
* **DELETE** `/api/profesores/{id}`
* **POST** `/api/profesores`

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
* **DELETE** `/api/contenidos/{id}`
* **POST** `/api/contenidos`

```json
{ "titulo": "Álgebra Lineal", "descripcion": "Fundamentos de espacios vectoriales" }
```

* **PUT** `/api/contenidos/{id}`

```json
{ "titulo": "Cálculo I", "descripcion": "Límites y derivadas" }
```

---

### Evaluaciones

* **GET** `/api/evaluaciones`
* **GET** `/api/evaluaciones/{id}`
* **DELETE** `/api/evaluaciones/{id}`
* **POST** `/api/evaluaciones`

```json
{
  "titulo": "Examen Parcial",
}
```

* **PUT** `/api/evaluaciones/{id}`

```json
{
  "titulo": "Examen Final",
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
