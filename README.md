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

# FullStack1

Proyecto semestral de desarrollo Full Stack – Semestre 3

## Descripción

Este repositorio contiene el proyecto semestral de la asignatura Full Stack del tercer semestre. La aplicación permite gestionar usuarios y tickets de soporte, exponiendo una API REST en el backend y una interfaz web en el frontend.

## Tecnologías

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate, Lombok
* **Base de datos:** H2 (por defecto) / MySQL (configurable en `application.properties`)
* **Frontend:** \[Indicar framework/librería: Angular, React, Vue.js o Thymeleaf]
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

### Ejemplos de POST

**Crear usuario**
Endpoint: `POST /api/usuarios`
Body JSON:

```json
{
  "rut": "33.333.333-3",
  "name": "Fede",
  "lastName": "Pereira",
  "mail": "Fede.Pereira@duoc.cl"
}
```

**Asignar ticket a soporte (path variable)**
Endpoint: `POST /api/soportes/1/tickets/5`
Body JSON: (no aplica)

**Asignar ticket a soporte (body JSON)**
Endpoint: `POST /api/soportes/1/tickets`
Body JSON:

```json
{
  "id": 5
}
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
Martin Gonzalez – [mi.gonzalezmu@duocuc.cl](mailto:mi.gonzalezmu@duocuc.cl)
Repositorio: [https://github.com/federico-pereira/FullStack1](https://github.com/federico-pereira/FullStack1)
