package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiRequest {

    private String apiUri;

    public ApiRequest(  ) {
        this.apiUri = "https://api.spotify.com";
    }

    private String sendRequest(String url) {
        String accessToken = Authorization.getToken();
        HttpClient client = HttpClient.newHttpClient();
        URI apiUri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(apiUri)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String responseBody = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        if (responseBody.contains("error")) {
            JsonObject jo = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject error = jo.getAsJsonObject("error");
            System.out.println(error.get("message").getAsString());
        }
        return responseBody;
    }

    public List<String> getNewReleases() {
        List<String> newReleases = new ArrayList<>();
        String url = apiUri + "/v1/browse/new-releases";
        String serverResponse = sendRequest(url);
        if (serverResponse.contains("error")) {
            return newReleases;
        }
        JsonObject jo = JsonParser.parseString(serverResponse).getAsJsonObject().get("albums").getAsJsonObject();
        for (JsonElement album: jo.getAsJsonArray("items")) {
            String albumStr = album.getAsJsonObject().get("name").getAsString();
            List<String> artists = new ArrayList<>();
            for (JsonElement artist: album.getAsJsonObject().get("artists").getAsJsonArray()) {
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            String artistStr = Arrays.toString(artists.toArray());
            String linkStr = album.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            newReleases.add(albumStr + "\n" + artistStr + "\n" + linkStr);
        }
        return newReleases;
    }

    public List<String> getFeatured() {
        List<String> featured = new ArrayList<>();
        String url = apiUri + "/v1/browse/featured-playlists";
        String serverResponse = sendRequest(url);
        if (serverResponse.contains("error")) {
            return featured;
        }
        JsonArray playlists = JsonParser.parseString(serverResponse).getAsJsonObject().get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement playlist: playlists) {
            String name = playlist.getAsJsonObject().get("name").getAsString();
            String link = playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            featured.add(name + "\n" + link);
        }
        return featured;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String url = apiUri + "/v1/browse/categories";
        String serverResponse = sendRequest(url);
        if (serverResponse.contains("error")) {
            return categories;
        }
        JsonArray jsonArr = JsonParser.parseString(serverResponse).getAsJsonObject().get("categories").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement item: jsonArr) {
            categories.add(item.getAsJsonObject().get("name").getAsString());
        }
        return categories;
    }

    public List<String> getPlaylists(String category) {
        List<String> playlists = new ArrayList<>();
        String categoryId = getCategory(category);
        if (categoryId.contains("unknown")) {
            System.out.println("Unknown category name.");
            return playlists;
        }
        String url = apiUri + "/v1/browse/categories/" + categoryId + "/playlists";
        System.out.println(url);
        String serverResponse = sendRequest(url);
        System.out.println(JsonParser.parseString(serverResponse).toString());
        if (serverResponse.contains("error")) {
            return playlists;
        }
        JsonArray jsonArr = JsonParser.parseString(serverResponse).getAsJsonObject().get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement item: jsonArr) {
            String name = item.getAsJsonObject().get("name").getAsString();
            String link = item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            playlists.add(name + "\n" + link);
        }
        return playlists;
    }

    private String getCategory(String category) {
        String url = apiUri + "/v1/browse/categories";
        String serverResponse = sendRequest(url);
        JsonArray categories = JsonParser.parseString(serverResponse).getAsJsonObject().get("categories").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement item: categories) {
            if (item.getAsJsonObject().get("name").getAsString().contains(category)) {
                return item.getAsJsonObject().get("id").getAsString();
            }
        }
        return "unknown";
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }
}
