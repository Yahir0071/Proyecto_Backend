package pe.edu.pe.Grupo02;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class Grupo02Application {

    public static void main(String[] args) {
        // Cambiamos la propiedad de control a una genérica para el navegador
        if (System.getProperty("browser.launcher.active") == null) {
            System.setProperty("browser.launcher.active", "true");

            // 1. Forzamos la creación automática de la BD física en Postgres
            crearBaseDeDatosSiNoExiste();

        }

        // 2. Arrancamos el backend PRIMERO (Esto bloquea el hilo hasta que termine)
        SpringApplication.run(Grupo02Application.class, args);
    }

    // ===== CONFIGURACIÓN CORS =====
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public ApplicationRunner levantarFrontendAlIniciar() {
        return args -> {
            automatizarEntornoHTML();
        };
    }

    private static void crearBaseDeDatosSiNoExiste() {
        String urlBase = "jdbc:postgresql://localhost:5432/template1";
        String usuario = "postgres";
        String contrasena = "1234";

        try (Connection conn = DriverManager.getConnection(urlBase, usuario, contrasena);
             Statement stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'almacen_db'");

            if (!rs.next()) {
                System.out.println("[INFO] La base de datos 'almacen_db' no existe. Creándola automáticamente...");
                stmt.executeUpdate("CREATE DATABASE almacen_db");
                System.out.println("[INFO] Base de datos 'almacen_db' creada con éxito.");
            } else {
                System.out.println("[INFO] Base de datos 'almacen_db' detectada y lista para operar.");
            }
        } catch (Exception e) {
            System.err.println("[ADVERTENCIA] No se pudo verificar/crear la BD automáticamente: " + e.getMessage());
            System.err.println("[ADVERTENCIA] Asegúrate de que PostgreSQL esté activo.");
        }
    }

    private static void automatizarEntornoHTML() {
        try {
            File directorioActual = new File(".").getCanonicalFile();
            File carpetaFrontend = new File(directorioActual.getParentFile(), "Proyecto_Fronted").getCanonicalFile();
            File archivoIndex = new File(carpetaFrontend, "index-login.html");

            System.out.println("[INFO] Ruta base del backend: " + directorioActual.getAbsolutePath());
            System.out.println("[INFO] Ruta del frontend HTML: " + carpetaFrontend.getAbsolutePath());

            if (!carpetaFrontend.exists() || !archivoIndex.exists()) {
                System.err.println("[ERROR] No se encontró la carpeta 'frontend' o el archivo 'index.html'.");
                return;
            }

            System.out.println("[INFO] Abriendo el archivo index.html en el navegador...");

            // Opción recomendada para Windows: Abre el archivo HTML directamente en el navegador por defecto
            ProcessBuilder pbFront = new ProcessBuilder(
                    "cmd.exe", "/c", "start \"\" \"" + archivoIndex.getAbsolutePath() + "\""
            );
            pbFront.start();

            System.out.println("[INFO] Todo listo. Iniciando sistema Backend...");
            Thread.sleep(1000);

        } catch (IOException | InterruptedException e) {
            System.err.println("[ERROR] Error al abrir el frontend HTML: " + e.getMessage());
        }
    }
}
