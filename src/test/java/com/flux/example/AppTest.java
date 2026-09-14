package com.flux.example;

import io.jettra.ee.JettraEE;
import io.jettra.ee.server.JettraEEServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite completa de pruebas de integración para JettraEEFluxExample.
 * Valida páginas JettraFlux, endpoints REST Jakarta EE, y especificaciones Eclipse MicroProfile.
 */
public class AppTest {

    private static final int TEST_PORT = 19080;
    private static JettraEEServer server;
    private static HttpClient client;

    @BeforeAll
    static void setUp() {
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        server = JettraEE.builder()
                .port(TEST_PORT)
                .contextPath("/")
                .title("JettraEEFlux Test API")
                .version("1.0.0")
                .scanPackages("com.flux", "jcf")
                .registerFluxPage("/", com.flux.example.pages.login.LoginPage.class)
                .build();

        server.start();
    }

    @AfterAll
    static void tearDown() {
        if (server != null) {
            server.stop();
        }
    }

    private String getUrl(String path) {
        return "http://localhost:" + TEST_PORT + path;
    }

    @Test
    @DisplayName("Página de Login responde HTTP 200")
    void testLoginPage() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/login")))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode());
        assertTrue(resp.body().contains("Login") || resp.body().contains("JettraFlux"),
                "El cuerpo debe contener contenido de la página de Login");
    }

    @Test
    @DisplayName("Página Forgot Password responde HTTP 200")
    void testForgotPasswordPage() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/forgot-password")))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode());
    }

    @Test
    @DisplayName("Página Dashboard responde HTTP 200 con sesión autenticada")
    void testDashboardPageWithAuth() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/dashboard")))
                .header("Cookie", "username=admin; role=ADMIN")
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode());
        assertTrue(resp.body().contains("Dashboard"), "Debe contener título o contenido de Dashboard");
    }

    @Test
    @DisplayName("Páginas E-Commerce responden HTTP 200 con sesión autenticada")
    void testECommercePages() throws IOException, InterruptedException {
        String[] paths = {
                "/product-overview",
                "/product-list",
                "/new-product",
                "/shopping-cart",
                "/checkout-form",
                "/order-summary",
                "/order-history"
        };

        for (String path : paths) {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(getUrl(path)))
                    .header("Cookie", "username=admin; role=ADMIN")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, resp.statusCode(), "La ruta " + path + " debe responder 200");
        }
    }

    @Test
    @DisplayName("Páginas Layout y Grid responden HTTP 200")
    void testLayoutAndGridPages() throws IOException, InterruptedException {
        String[] paths = {
                "/grid-demo",
                "/card-demo"
        };

        for (String path : paths) {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(getUrl(path)))
                    .header("Cookie", "username=admin; role=ADMIN")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, resp.statusCode(), "La ruta " + path + " debe responder 200");
        }
    }

    @Test
    @DisplayName("Páginas Apps responden HTTP 200")
    void testAppsPages() throws IOException, InterruptedException {
        String[] paths = {
                "/chat",
                "/files",
                "/tasklist",
                "/mail-inbox"
        };

        for (String path : paths) {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(getUrl(path)))
                    .header("Cookie", "username=admin; role=ADMIN")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, resp.statusCode(), "La ruta " + path + " debe responder 200");
        }
    }

    @Test
    @DisplayName("Páginas Example y Reglas responden HTTP 200")
    void testExampleAndRulesPages() throws IOException, InterruptedException {
        String[] paths = {
                "/person",
                "/person-crud",
                "/reglas",
                "/rules"
        };

        for (String path : paths) {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(getUrl(path)))
                    .header("Cookie", "username=admin; role=ADMIN")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, resp.statusCode(), "La ruta " + path + " debe responder 200");
        }
    }

    @Test
    @DisplayName("Páginas UI Kit Componentes responden HTTP 200")
    void testUIKitPages() throws IOException, InterruptedException {
        String[] paths = {
                "/input",
                "/forms",
                "/button-demo",
                "/table",
                "/dataview",
                "/panel",
                "/tree"
        };

        for (String path : paths) {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(getUrl(path)))
                    .header("Cookie", "username=admin; role=ADMIN")
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, resp.statusCode(), "La ruta " + path + " debe responder 200");
        }
    }

    @Test
    @DisplayName("API Jakarta REST /api/persons responde JSON y soporta operaciones")
    void testRestApiPersons() throws IOException, InterruptedException {
        // GET /api/persons
        HttpRequest getReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/api/persons")))
                .GET()
                .build();
        HttpResponse<String> getResp = client.send(getReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResp.statusCode());
        assertTrue(getResp.body().contains("Alice Johnson") || getResp.body().contains("name"),
                "Debe devolver la lista de personas en JSON");

        // POST /api/persons
        String newPersonJson = "{\"name\":\"Test Person\",\"email\":\"test@jettra.io\",\"age\":30}";
        HttpRequest postReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/api/persons")))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newPersonJson))
                .build();
        HttpResponse<String> postResp = client.send(postReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResp.statusCode());
    }

    @Test
    @DisplayName("Endpoints Eclipse MicroProfile (Health, Metrics, OpenAPI, Swagger UI)")
    void testMicroProfileEndpoints() throws IOException, InterruptedException {
        // Health
        HttpRequest healthReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/q/health")))
                .GET()
                .build();
        HttpResponse<String> healthResp = client.send(healthReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, healthResp.statusCode());
        assertTrue(healthResp.body().contains("UP"));

        // Metrics
        HttpRequest metricsReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/q/metrics")))
                .GET()
                .build();
        HttpResponse<String> metricsResp = client.send(metricsReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, metricsResp.statusCode());

        // OpenAPI
        HttpRequest openApiReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/q/openapi")))
                .GET()
                .build();
        HttpResponse<String> openApiResp = client.send(openApiReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, openApiResp.statusCode());
        assertTrue(openApiResp.body().contains("openapi") || openApiResp.body().contains("Person"));

        // Swagger UI
        HttpRequest swaggerReq = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/q/swagger-ui")))
                .GET()
                .build();
        HttpResponse<String> swaggerResp = client.send(swaggerReq, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, swaggerResp.statusCode());
        assertTrue(swaggerResp.body().contains("swagger-ui") || swaggerResp.body().contains("Swagger"));
    }

    @Test
    @DisplayName("Página estática index.html en Document Root responde HTTP 200")
    void testIndexHtmlWelcomeFile() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/index.html")))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode());
        assertTrue(resp.headers().firstValue("Content-Type").orElse("").contains("text/html"));
        assertTrue(resp.body().contains("JettraEEFlux Showcase Portal") || resp.body().contains("JettraFlux"));
    }

    @Test
    @DisplayName("Protección Servlet 6.0: /WEB-INF/web.xml y /WEB-INF/beans.xml retornan HTTP 403")
    void testProtectedWebInfForbidden() throws IOException, InterruptedException {
        HttpRequest reqWebXml = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/WEB-INF/web.xml")))
                .GET()
                .build();
        HttpResponse<String> resWebXml = client.send(reqWebXml, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, resWebXml.statusCode(), "El acceso a /WEB-INF/web.xml debe estar protegido con HTTP 403");

        HttpRequest reqBeansXml = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/WEB-INF/beans.xml")))
                .GET()
                .build();
        HttpResponse<String> resBeansXml = client.send(reqBeansXml, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, resBeansXml.statusCode(), "El acceso a /WEB-INF/beans.xml debe estar protegido con HTTP 403");
    }

    @Test
    @DisplayName("Protección Servlet 6.0: /META-INF/beans.xml retorna HTTP 403")
    void testProtectedMetaInfForbidden() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/META-INF/beans.xml")))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, resp.statusCode(), "El acceso a /META-INF/* debe estar protegido con HTTP 403");
    }

    @Test
    @DisplayName("Recurso estático CSS en src/main/webapp/css/style.css responde HTTP 200 y Content-Type text/css")
    void testStaticWebappResourceServing() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(getUrl("/css/style.css")))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, resp.statusCode());
        assertTrue(resp.headers().firstValue("Content-Type").orElse("").contains("text/css"));
        assertTrue(resp.body().contains(".card") || resp.body().contains("var(--primary)"));
    }

    @Test
    @DisplayName("Descriptores estándar Jakarta EE cargados correctamente en el servidor")
    void testStandardJakartaEEDescriptorsLoaded() {
        assertNotNull(server.getWebResourceManager(), "WebResourceManager debe estar inicializado en el servidor");
        assertEquals("src/main/webapp", server.getWebResourceManager().getWebappRoot(), "El Document Root debe ser src/main/webapp");
        assertTrue(server.getWebResourceManager().getWelcomeFiles().contains("index.html"), "web.xml debe registrar index.html");

        assertEquals("annotated", io.jettra.ee.jakarta.cdi.JettraCDIContainer.getBeanDiscoveryMode(),
                "beans.xml debe configurar bean-discovery-mode='annotated'");
    }
}
