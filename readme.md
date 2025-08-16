# Literalura

**Literalura** es una aplicación de consola en Java que funciona como una biblioteca personal, permitiendo gestionar libros y autores de manera sencilla. La aplicación se conecta a la API **Gutendex** para buscar libros por título y guarda los datos en una base de datos local.

---

## Tecnologías

- Java 21+
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- API: [Gutendex](https://gutendex.com/)
- Maven (gestión de dependencias)

---

## Funcionalidades

1. Buscar libros por título usando la API Gutendex.
2. Listar todos los libros registrados en la base de datos.
3. Listar todos los autores registrados.
4. Listar autores vivos en un año específico.
5. Listar libros filtrados por idioma.
6. Salir de la aplicación.

---

## Instalación

1. Clonar el repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
```

2. Configurar la base de datos PostgreSQL:
   - Crear la base de datos `literalura_db`.
   - Configurar `application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Construir el proyecto con Maven:

```bash
mvn clean install
```

4. Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

---

## Uso

Al iniciar la aplicación se muestra un menú interactivo por consola:

```
*****************************************
* ¡Bienvenido a Literalura!             *
* Tu biblioteca personal                *
*****************************************
+-------------------------------+
|        MENÚ DE OPCIONES       |
+-------------------------------+
| 1 - Buscar libro por título   |
| 2 - Listar libros registrados|
| 3 - Listar autores registrados|
| 4 - Listar autores vivos en un año |
| 5 - Listar libros por idioma |
| 6 - Salir                     |
+-------------------------------+
```

- **Buscar libro por título**: Permite consultar la API y guardar el libro junto con su autor en la base de datos si no existe.
- **Listar libros registrados**: Muestra todos los libros almacenados.
- **Listar autores registrados**: Muestra todos los autores registrados.
- **Listar autores vivos**: Filtra autores según el año ingresado y su fecha de fallecimiento.
- **Listar libros por idioma**: Permite seleccionar un idioma y mostrar todos los libros disponibles en ese idioma.

Ejemplo de salida de un libro:

```
------------------------------
             LIBRO            
------------------------------
Titulo: Orgullo y Prejuicio
Autor: Jane Austen
Idioma: en
N° de descargas: 5000
******************************
```

Ejemplo de salida de un autor:

```
===============================
Nombre: Jane Austen
Fecha De Nacimiento: 1775-12-16
Fecha De Fallecimiento: 1817-07-18
Libros del autor: [Orgullo y Prejuicio, Sentido y Sensibilidad]
===============================
```

---

## Notas

- La aplicación requiere conexión a Internet para buscar libros mediante la API Gutendex.
- La base de datos se maneja con JPA y Hibernate, por lo que los cambios en entidades se reflejan automáticamente al iniciar la aplicación si `spring.jpa.hibernate.ddl-auto=update`.

