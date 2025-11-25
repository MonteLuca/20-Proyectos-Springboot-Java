# Proyecto 1: Gestor de Notas Personales 📝

## Objetivo
Desarrollar un **CRUD completo de notas personales** utilizando **Spring Boot**, **JPA** y **REST API**, aplicando buenas prácticas de validación y manejo de excepciones.  
Este proyecto pertenece al **Módulo 1 – CRUD básicos** de la serie de 20 proyectos Spring Boot.

---

## Requisitos del proyecto

### 1. Entidad `Note`
- **Campos obligatorios:**  
  - `id`: Long, autogenerado  
  - `title`: String, obligatorio, entre 1-255 caracteres  
  - `content`: String, obligatorio, entre 1-1000 caracteres  
  - `createdAt`: LocalDateTime, autogenerado al crear la nota  

### 2. Endpoints REST
- `POST /api/notes` → Crear nota  
- `GET /api/notes` → Listar todas las notas  
- `GET /api/notes/{id}` → Obtener nota por ID  
- `PUT /api/notes/{id}` → Actualizar nota por ID  
- `DELETE /api/notes/{id}` → Eliminar nota por ID  

### 3. Validaciones y manejo de excepciones
- Validaciones de campos obligatorios y rango de caracteres  
- Exception handling global para:  
  - Nota no encontrada (`NoteNotFoundException`)  
  - Argumentos inválidos (`MethodArgumentNotValidException`)  
  - Errores generales (`Exception`)  

### 4. Restricciones técnicas
- No usar DTOs ni relaciones entre entidades  
- Arquitectura en capas: Controller → Service → Repository  
- Persistencia con Spring Data JPA y base de datos MySQL  
- Uso de **Lombok** para getters/setters y constructores  

---

## Reto opcional (Bonus)
1. ✅ Añadir un campo `lastModified` que se actualice automáticamente al editar la nota  
2. ✅ Añadir logging para registrar operaciones CRUD  

---

## 📋 Tareas Realizadas

### Arquitectura del Proyecto
- ✅ Configuración del proyecto Spring Boot 3.5.7 con Java 21
- ✅ Estructura en capas: Controller → Service → Repository
- ✅ Uso de Spring Data JPA para la persistencia de datos
- ✅ Integración con MySQL como base de datos

### Entidad y Persistencia
- ✅ Creación de la entidad `Note` con validaciones usando Bean Validation
- ✅ Configuración de JPA con `@Entity`, `@Table`, y anotaciones de Hibernate
- ✅ Implementación de campos automáticos:
  - `id`: Generado automáticamente con `@GeneratedValue`
  - `createdAt`: Generado automáticamente con `@CreationTimestamp` (no actualizable)
  - `lastModified`: Actualizado automáticamente con `@UpdateTimestamp` (bonus)
- ✅ Validaciones implementadas:
  - `@NotBlank` para campos obligatorios
  - `@Size` para límites de caracteres (title: 255, content: 1000)

### Capa de Servicio
- ✅ Implementación de la interfaz `NoteService` con todos los métodos CRUD
- ✅ Implementación en `NoteServiceImpl` con manejo de transacciones (`@Transactional`)
- ✅ Búsqueda de notas por ID con validación de existencia
- ✅ Actualización de notas preservando el ID y `createdAt`, actualizando solo `title`, `content` y `lastModified`
- ✅ Eliminación de notas con validación previa de existencia
- ✅ **Logging implementado** (bonus): Registro de todas las operaciones CRUD usando `@Slf4j`

### Capa de Controlador
- ✅ Creación de `NoteController` con mapeo REST `/api/notes`
- ✅ Implementación de todos los endpoints:
  - `POST /api/notes` → Retorna 201 Created
  - `GET /api/notes` → Retorna 200 OK con lista de notas
  - `GET /api/notes/{id}` → Retorna 200 OK con nota específica
  - `PUT /api/notes/{id}` → Retorna 200 OK con nota actualizada
  - `DELETE /api/notes/{id}` → Retorna 204 No Content
- ✅ Uso de `@Valid` para validación automática de entidades
- ✅ Inyección de dependencias con `@RequiredArgsConstructor` (Lombok)

### Manejo de Excepciones
- ✅ Creación de excepción personalizada `NoteNotFoundException`
- ✅ Implementación de `ExceptionHandlerGlobal` con `@RestControllerAdvice`
- ✅ Manejo de excepciones:
  - `NoteNotFoundException` → 404 Not Found con mensaje personalizado
  - `MethodArgumentNotValidException` → 400 Bad Request con detalles de validación
  - `Exception` genérica → 500 Internal Server Error
- ✅ Creación de clase `ErrorResponse` con Builder pattern (Lombok) para respuestas de error estandarizadas

### Repositorio
- ✅ Creación de `NoteRepository` extendiendo `JpaRepository<Note, Long>`
- ✅ Uso de métodos estándar de Spring Data JPA (`findAll`, `findById`, `save`, `deleteById`, `existsById`)

### Configuración
- ✅ Configuración de MySQL en `application.yml`:
  - Conexión a base de datos `notas` (creación automática si no existe)
  - Configuración de Hibernate para actualización automática del esquema
  - Formato SQL habilitado para debugging
- ✅ Configuración del puerto del servidor (8080)
- ✅ Configuración de credenciales de base de datos (username: root, password: root)

### Testing con Postman
- ✅ Creación de colección de Postman completa con todos los endpoints
- ✅ Configuración de variables de entorno (`base_url`, `api_path`)
- ✅ Tests automatizados en cada request para validar códigos de estado HTTP
- ✅ Documentación completa de la API incluida en la colección

---

## 🚀 Configuración y Uso

### Requisitos Previos
1. **MySQL** corriendo en `localhost:3306`
2. Base de datos `notas` (se crea automáticamente si no existe)
3. Credenciales configuradas en `application.yml`:
   - Username: `root`
   - Password: `root`
   - ⚠️ **Importante**: Modifica estas credenciales en `src/main/resources/application.yml` si tus credenciales de MySQL son diferentes

### Ejecutar el Proyecto
1. Ejecuta la aplicación Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```
   O en Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

2. La API estará disponible en: `http://localhost:8080/api/notes`

---

## 📬 Colección de Postman

Se incluye una colección completa de Postman para probar todos los endpoints de la API.

### Importar la Colección

1. **Abrir Postman**
2. **Importar colección:**
   - Clic en "Import" (esquina superior izquierda)
   - Selecciona el archivo `../postman/API_Gestor_de_Notas_Personales.json`
   - O arrastra y suelta el archivo en Postman

3. **Configurar Variables (Opcional pero recomendado):**
   - La colección ya incluye variables por defecto:
     - `base_url`: `http://localhost:8080`
     - `api_path`: `/api/notes`
   - Puedes crear un entorno en Postman y modificar estas variables si necesitas cambiar la URL base

### Endpoints Incluidos en la Colección

- ✅ **Crear Nota** (POST) - Con body de ejemplo y test automatizado
- ✅ **Buscar todas las notas** (GET) - Con test automatizado
- ✅ **Buscar nota por ID** (GET) - Busca la nota con ID 1
- ✅ **Actualizar Nota** (PUT) - Con body de ejemplo y test automatizado
- ✅ **Eliminar Nota** (DELETE) - Con test automatizado

### Notas sobre la Colección

- Todos los requests incluyen tests automatizados que validan los códigos de estado HTTP
- La colección está lista para usar, solo asegúrate de que la aplicación esté corriendo
- Puedes modificar los IDs y cuerpos de las peticiones según tus necesidades
- La documentación completa de cada endpoint está incluida en la descripción de la colección

---

## 📝 Ejemplos de Uso

### Crear una Nota
```http
POST http://localhost:8080/api/notes
Content-Type: application/json

{
  "title": "Mi primera nota",
  "content": "Este es el contenido de mi primera nota"
}
```

### Obtener Todas las Notas
```http
GET http://localhost:8080/api/notes
```

### Obtener una Nota por ID
```http
GET http://localhost:8080/api/notes/1
```

### Actualizar una Nota
```http
PUT http://localhost:8080/api/notes/1
Content-Type: application/json

{
  "title": "Título actualizado",
  "content": "Contenido actualizado"
}
```

### Eliminar una Nota
```http
DELETE http://localhost:8080/api/notes/1
```

---

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.7**
- **Java 21**
- **Spring Data JPA**
- **MySQL 8**
- **Lombok**
- **Bean Validation (Jakarta Validation)**
- **Hibernate**
- **SLF4J** (Logging)

---

## 📊 Estructura del Proyecto

```
notes/
├── src/
│   ├── main/
│   │   ├── java/com/lucam/gestor_notas_personales/
│   │   │   ├── controllers/
│   │   │   │   └── NoteController.java
│   │   │   ├── entities/
│   │   │   │   └── Note.java
│   │   │   ├── exceptions/
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   ├── ExceptionHandlerGlobal.java
│   │   │   │   └── NoteExceptions/
│   │   │   │       └── NoteNotFoundException.java
│   │   │   ├── repositories/
│   │   │   │   └── NoteRepository.java
│   │   │   ├── services/
│   │   │   │   ├── NoteService.java
│   │   │   │   └── impl/
│   │   │   │       └── NoteServiceImpl.java
│   │   │   └── GestorNotasPersonalesApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application.yml
│   └── test/
└── ../postman/
    └── API_Gestor_de_Notas_Personales.json
```

---

## 👨‍💻 Créditos

Este proyecto forma parte de una serie de **20 desafíos de proyectos Java y Spring Boot** creados por:

**José Luis Rodríguez Valenzuela**

- 🔗 [LinkedIn](https://www.linkedin.com/in/joseluispayoyo/)

Agradecimientos especiales por la creación y diseño de estos desafíos que ayudan a practicar y fortalecer las habilidades en desarrollo backend con Spring Boot.

---

