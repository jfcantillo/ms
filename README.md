# Microservicios: Producto e Inventario

## 📦 Documentación del Proyecto

### 🐳 Docker y Docker Compose

Este proyecto contiene dos microservicios containerizados con Docker: **Producto** e **Inventario**.

#### 📁 Estructura

```
├── docker-compose.yml
├── producto-service/
│   ├── Dockerfile
├── inventario-service/
│   ├── Dockerfile
```

#### ▶️ Instrucciones para levantar los servicios

1. **Compilar los JARs**:

```bash
cd producto-service
./mvnw clean package -DskipTests

cd ../inventario-service
./mvnw clean package -DskipTests
```

2. **Ejecutar con Docker Compose**:

```bash
docker-compose up --build
```

- `http://localhost:8080` → Producto Service
- `http://localhost:8081` → Inventario Service

3. **Detener los servicios**:

```bash
docker-compose down
```

---

### 🔐 Autenticación entre microservicios

Ambos servicios utilizan **API Key** a través del header:

```http
X-API-KEY: supersecreta123
```

Esto se valida mediante un filtro `ApiKeyFilter.java` en cada servicio.

---

### 🔍 Swagger / OpenAPI

Cada microservicio incluye documentación Swagger accesible vía:

- `http://localhost:8080/swagger-ui/index.html` (Producto)
- `http://localhost:8081/swagger-ui/index.html` (Inventario)

Los esquemas siguen la especificación **JSON:API** (`application/vnd.api+json`).

---

### 🔁 Git Flow

Se utilizó **Git Flow** siguiendo estas ramas:

- `main`: Producción
- `develop`: Integración
- `feature/*`: Nuevas funcionalidades (ej. `feature/crear-producto`)
- `release/*`: Preparación de versiones
- `hotfix/*`: Parches en producción

---

### 🧪 Testing

Cada microservicio incluye:

- ✅ Pruebas unitarias para:
  - Creación de productos
  - Gestión de inventario
  - Manejo de errores (producto no encontrado, stock insuficiente)
- 🔁 Prueba de integración mínima (por servicio)
- 🧪 Se usa `@SpringBootTest`, `@WebMvcTest`, `MockMvc`, `TestRestTemplate`

Estructura:

```
src/test/java/com/example/producto/ProductoControllerTest.java
src/test/java/com/example/inventario/InventarioControllerTest.java
```

### Diagrama de Arquitectura General (Microservicios)
    
                   ┌────────────────────┐
                   │     Cliente        │
                   │ (Postman / UI App) │
                   └────────┬───────────┘
                            │
                            ▼
                   ┌────────────────────┐
                   │    API Gateway     │ (opcional)
                   └────────┬───────────┘
                            │
         ┌──────────────────┴──────────────────┐
         ▼                                     ▼
┌────────────────────┐              ┌────────────────────┐
│ Microservicio      │              │ Microservicio      │
│    Producto        │              │    Inventario      │
│ (Spring Boot)      │              │ (Spring Boot)      │
└────────┬───────────┘              └────────┬───────────┘
         │                                   │
         ▼                                   ▼
 ┌───────────────┐                    ┌───────────────┐
 │ Base de Datos │                    │ Base de Datos │
 │   producto_db │                    │ inventario_db │
 └───────────────┘                    └───────────────┘


### Flujo de Compra e Interacción entre Microservicios

Cliente
  │
  ├─▶ POST /inventario/compras (productoId, cantidad)
  │
  ▼
Inventario Service
  ├─▶ GET /producto/{id}  (valida existencia y precio)
  │     (usa API Key + JSON:API)
  │
  ├─▶ Verifica stock disponible en su DB
  │
  ├─▶ Si hay stock, actualiza cantidad
  │
  └─▶ Retorna éxito o error (sin stock / producto no existe)
