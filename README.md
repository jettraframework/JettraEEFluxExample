# JettraEEFluxExample

**JettraEEFluxExample** es el proyecto de referencia de última generación que une la potencia, velocidad y estándares de **JettraEE** (servidor ultra-ligero y reactivo compatible con **Eclipse MicroProfile** y **Jakarta EE 11/12**, potenciado por **Java 25 Virtual Threads - Project Loom**) con la suite completa de interfaces web reactivas de **JettraFlux** y el motor de validación de reglas **JettraRules**.

---

## 🚀 Arquitectura y Tecnologías

- **Servidor Backend**: [JettraEE](file:///home/avbravo/NetBeansProjects/jettrastack_local/JettraWorkspace/JettraEE)
  - Java 25 con Virtual Threads Loom (alta concurrencia con mínimo uso de memoria).
  - Jakarta REST 3.1 (`@Path`, `@GET`, `@POST`, `@PUT`, `@DELETE`, `@Produces`, `@Consumes`).
  - Jakarta CDI 4.1 (`@ApplicationScoped`, `@RequestScoped`, `@Inject`).
  - Jakarta Validation 3.0 (`@Valid`, validación declarativa de entidades).
  - Eclipse MicroProfile 6.x/7.x:
    - **Config**: Inyección `@ConfigProperty` y configuración unificada.
    - **Health**: Endpoints `/q/health`, `/q/health/live`, `/q/health/ready`.
    - **Metrics**: Endpoints `/q/metrics` compatibles con Prometheus.
    - **OpenAPI / Swagger UI**: Generación dinámica en `/q/openapi` y visualizador interactivo en `/q/swagger-ui`.
    - **Rest Client**: Clientes REST tipados `@RegisterRestClient`.
- **Frontend y UI Reactiva**: [JettraFlux](file:///home/avbravo/NetBeansProjects/jettrastack_local/JettraWorkspace/JettraFlux)
  - Layout empresarial (`TemplatePage`) con TopBar, Sidebar responsivo, selector de temas y conmutador de idiomas.
  - Componentes de alta densidad (StatCard, VisitorGraphCard, Datatable, Grid, Card, etc.).
- **Motor de Reglas**: [JettraRules](file:///home/avbravo/NetBeansProjects/jettrastack_local/JettraWorkspace/JettraRules)
  - Validación dinámica y reactiva de formularios y cálculos de negocio (`PersonModel`, `ReglasModel`, facturación).

---

## 📦 Mapa de Rutas y Funcionalidades

### 1. Autenticación y Seguridad
| Ruta | Descripción |
|---|---|
| `/` o `/login` | Página principal de inicio de sesión con soporte de credenciales y roles. |
| `/forgot-password` | Recuperación de contraseñas. |

> **Usuarios de prueba preconfigurados**:
> - `admin` / `admin` (Rol: `ADMIN`)
> - `demo` / `demo` (Rol: `DEMO`)
> - `avbravo` / `avbravo` (Rol: `ADMIN`)

### 2. Panel Principal (Dashboard)
| Ruta | Descripción |
|---|---|
| `/dashboard` | Cuadro de mando ejecutivo con tarjetas estadísticas (`StatCard`), gráfico de visitantes e historial de transacciones. |

### 3. Suite E-Commerce
| Ruta | Descripción |
|---|---|
| `/product-overview` | Vista detallada de producto con galería multimedia y selector de variantes. |
| `/product-list` | Catálogo de productos en cuadrícula interactiva. |
| `/new-product` | Formulario de alta de productos comerciales. |
| `/shopping-cart` | Carrito de compras con cálculo reactivo de totales. |
| `/checkout-form` | Pasarela y formulario de facturación y despacho. |
| `/order-summary` | Resumen de orden y desglose de pago. |
| `/order-history` | Histórico de órdenes y seguimiento. |

### 4. Suite Apps Colaborativas
| Ruta | Descripción |
|---|---|
| `/chat` | Módulo de mensajería instantánea. |
| `/mail-inbox` | Bandeja de entrada de correo electrónico y carpetas. |
| `/tasklist` | Gestor de tareas pendientes y estados. |
| `/files` | Administrador de archivos y almacenamiento. |
| `/file` | Vista de detalle de archivo. |

### 5. Layout, Grid y UI Kit
| Ruta | Descripción |
|---|---|
| `/grid-demo` | Demostración de grid CSS responsivo con inyección de MicroProfile Config. |
| `/card-demo` | Demostración de cards y contenedores visuales. |
| `/input` | Catálogo de entradas y campos de texto. |
| `/forms` | Formularios completos con validaciones. |
| `/button-demo` | Variantes de botones, estados y estilos. |
| `/table` y `/dataview` | Tablas de datos y vistas de colección. |
| `/panel`, `/overlay`, `/tree`, `/menu`, `/message`, `/timeline`, `/misc` | Componentes visuales UI Kit. |

### 6. Suite Personas y Reglas de Negocio (JettraRules)
| Ruta | Descripción |
|---|---|
| `/person` | Formulario reactivo con validaciones browser/servidor con `JettraRules`. |
| `/person-crud` | CRUD completo con barra de herramientas, búsqueda, datatable y acciones. |
| `/reglas` / `/rules` | Motor de cálculo de reglas de negocio en tiempo real. |

### 7. API REST Jakarta EE y Eclipse MicroProfile
| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/persons` | Lista todas las personas en formato JSON (CDI inyectado). |
| `POST` | `/api/persons` | Registra una nueva persona. |
| `PUT` | `/api/persons` | Actualiza una persona existente. |
| `DELETE` | `/api/persons/{id}` | Elimina una persona por nombre/identificador. |
| `GET` | `/q/health` | Estado de salud del servidor (`UP`, memoria, procesadores). |
| `GET` | `/q/metrics` | Métricas de rendimiento y conteo de peticiones (OpenMetrics). |
| `GET` | `/q/openapi` | Especificación OpenAPI 3.1 autogenerada. |
| `GET` | `/q/swagger-ui` | Interfaz gráfica interactiva Swagger UI. |

---

## 🛠️ Compilación y Ejecución

### Requisitos
- **JDK 25** (o 21 con preview habilitado).
- **Maven 3.9+**.

### 1. Ejecutar Pruebas Automatizadas
```bash
mvn clean test
```

### 2. Iniciar la Aplicación
```bash
mvn exec:java
```
O especificando puerto y contextPath:
```bash
mvn exec:java -Dexec.args="--port 8080 --context-path /"
```

Una vez iniciado, abre tu navegador en:
- Interfaz Web: **http://localhost:8080/**
- Swagger UI: **http://localhost:8080/q/swagger-ui**
- Métricas: **http://localhost:8080/q/metrics**
- Health: **http://localhost:8080/q/health**

---

## 🏗️ Estructura del Proyecto (100% Compatible con Especificaciones Jakarta EE)

El proyecto sigue estrictamente el estándar de proyectos **Jakarta EE 10/11**:

```
JettraEEFluxExample/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/                         # Código fuente Java
    │   │   ├── com/flux/example/         # Aplicación, Health, Pages Login/Dashboard
    │   │   └── com/flux/plugin/example/  # Controladores REST, Servicios CDI, Páginas JettraFlux
    │   ├── resources/                    # Recursos Classpath
    │   │   ├── application.properties
    │   │   ├── messages_es.properties
    │   │   ├── messages_en.properties
    │   │   ├── messages.properties
    │   │   └── META-INF/
    │   │       ├── beans.xml             # Descriptor CDI Classpath (Helidon/JAR)
    │   │       └── microprofile-config.properties # Configuración MicroProfile
    │   └── webapp/                       # Document Root Oficial de la Aplicación Web
    │       ├── WEB-INF/                  # Zona protegida HTTP (Servlet 6.0 §10.5)
    │       │   ├── web.xml               # Descriptor Servlet y Welcome Files
    │       │   ├── beans.xml             # Descriptor CDI Web (WAR / Payara Micro)
    │       │   └── faces-config.xml      # Configuración Jakarta Faces 4.0
    │       ├── index.html                # Welcome file portal principal
    │       └── css/
    │           └── style.css             # Recursos estáticos web
    └── test/
        └── java/
            └── com/flux/example/
                └── AppTest.java           # 15 Pruebas automatizadas de integración
```

---

## 🔄 Portabilidad Multi-Servidor

El proyecto puede desplegarse y ejecutarse sin cambios en múltiples servidores del ecosistema Jakarta EE y MicroProfile:

### 1. Servidor JettraEE (Modo Nativo Ultra-Rápido con Virtual Threads)
```bash
# Ejecución en desarrollo
mvn compile exec:java

# O empaquetado JAR ejecutable standalone
mvn clean package
java --enable-preview -jar target/JettraEEFluxExample-1.0.0-SNAPSHOT.jar
```

### 2. Despliegue en Payara Micro
```bash
# Empaquetar WAR estándar
mvn clean package -Pwar

# Desplegar en Payara Micro
java -jar payara-micro.jar --deploy target/JettraEEFluxExample-1.0.0-SNAPSHOT.war
```

### 3. Ejecución en Helidon / WildFly
- **Helidon MP**: Compatible gracias a `src/main/resources/META-INF/beans.xml` y `microprofile-config.properties`.
- **WildFly**: Desplegar el archivo `.war` generado con el perfil `-Pwar` en la carpeta `deployments/`.

