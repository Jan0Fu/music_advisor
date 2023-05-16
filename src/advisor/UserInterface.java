package advisor;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scan;
    private final Authorization authorization;
    private final ApiRequest apiRequest;
    private String authUri;
    private String apiUri;
    private int itemsPage;
    private boolean isAuthenticated;

    public UserInterface() {
        this.authUri = "https://accounts.spotify.com";
        this.apiUri = "https://api.spotify.com";
        this.itemsPage = 5;
        this.scan = new Scanner(System.in);
        this.authorization = new Authorization();
        this.apiRequest = new ApiRequest(apiUri);
        this.isAuthenticated = false;
    }

    public void start(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                authUri = args[i + 1];
            } else if (args[i].equals("-resource")) {
                apiUri = args[i + 1];
            } else if (args[i].equals("-page")) {
                itemsPage = Integer.parseInt(args[i + 1]);
            }
        }
        while (true) {
            String userInput = scan.nextLine();
            String[] inputs = userInput.split(" ");
            switch (inputs[0]) {
                case "auth" -> isAuthenticated = authorization.authorize(authUri);
                case "new" -> newReleases();
                case "featured" -> featured();
                case "categories" -> categories();
                case "playlists" -> playlists(inputs[1]);
                case "exit" -> exit();
                default -> System.out.println("wrong input");
            }
        }
    }

    private void newReleases() {
        if (isAuthenticated) {
            System.out.println(apiRequest.getNewReleases());

        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void featured() {
        if (isAuthenticated) {
            System.out.println(apiRequest.getFeatured());
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void categories() {
        if (isAuthenticated) {
            System.out.println(apiRequest.getCategories());
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void playlists(String category) {
        if (isAuthenticated) {
            System.out.println(apiRequest.getPlaylists(category));
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void exit() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
}
