package es.dsuarez.microservices;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UltraTinyHttpServer implements AutoCloseable {

    private static final int DEFAULT_PORT = 8080;
    private static final int HTTP_OK_STATUS = 200;

    private final HttpServer httpServer;

    private static final Logger LOG = LogManager.getLogger(UltraTinyHttpServer.class);

    private UltraTinyHttpServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 0);

        httpServer.createContext("/", this::handle);

        httpServer.setExecutor(null);
        httpServer.start();
        LOG.info("Server started, can be accessed on http://localhost:{}", DEFAULT_PORT);
    }

    private void handle(HttpExchange httpExchange) throws IOException {
        String response = "<h1>Otro proyecto!</h1>";
        httpExchange.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    @Override
    public void close() {
        httpServer.stop(0);
    }

    public static void main(String[] args) throws IOException {
        new UltraTinyHttpServer();
    }
}
