package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Authorization {

    private final String clientId = "d7792bd713af4b8a8f100cf307a7c8bd";
    private final String clientSecret = "ec5029c016124c9da150cfd06f995409";
    private String accessCode = "";
    private String accessToken = "";

    public boolean authorize(String authPath) {

        try {
            System.out.println("use this link to request the access code:\n" +
                    authPath + "/authorize?client_id=" + clientId + "&redirect_uri=http://localhost:8080/&response_type=code\n" +
                    "waiting for code...");

            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    new HttpHandler() {
                        @Override
                        public void handle(HttpExchange exchange) throws IOException {
                            String query = exchange.getRequestURI().getQuery();
                            String code = query != null ? query.split("=")[1] : "";

                            if (query != null && query.split("=")[0].equals("code")) {
                                accessCode = code;
                                System.out.println(accessCode);
                                query = "Got the code. Return back to your program.";
                            } else {
                                query = "Authorization code not found. Try again.";
                            }
                            exchange.sendResponseHeaders(200, query.length());
                            exchange.getResponseBody().write(query.getBytes());
                            exchange.getResponseBody().close();
                        }
                    });
            server.start();
            while (accessCode.isEmpty()) {
                Thread.sleep(10);
            }
            server.stop(10);
            System.out.println("code received");

            if (authRequest(authPath)) {
                return true;
            }

        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private boolean authRequest(String authPath) {
        if (!accessCode.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create(authPath + "/api/token"))
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + accessCode +
                            "&redirect_uri=http://localhost:8080/&client_id=" + clientId + "&client_secret=" + clientSecret))
                    .build();
            try {
                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
                accessToken = jo.get("access_token").getAsString();
                System.out.println(response.body());
                System.out.println("---SUCCESS---");
                return true;

            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Authorization code not found. Try again.");
        }
        return false;
    }


}
