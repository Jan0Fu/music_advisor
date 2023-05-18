package advisor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static advisor.Pagination.printPages;

public class UserInterface {

    private final Scanner scan;
    private final Authorization authorization;
    private final ApiRequest apiRequest;
    private int itemsPage;
    private boolean isAuthorized;

    public UserInterface() {
        this.itemsPage = 5;
        this.scan = new Scanner(System.in);
        this.authorization = new Authorization();
        this.apiRequest = new ApiRequest();
        this.isAuthorized = false;
    }

    public void start(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                authorization.setAuthUri(args[i + 1]);
            }
            if (args[i].equals("-resource")) {
                apiRequest.setApiUri(args[i + 1]);
            }
            if (args[i].equals("-page")) {
                itemsPage = Integer.parseInt(args[i + 1]);
            }
        }

        List<String> allInfo = new ArrayList<>();
        int pageNumber = 1;

        while (true) {
            String userInput = scan.nextLine();
            String[] inputs = userInput.split(" ");
            if (!isAuthorized && !inputs[0].equals("auth") && !inputs[0].equals("exit")) {
                System.out.println("Please, provide access for application.");
            }
            switch (inputs[0]) {
                case "auth" -> isAuthorized = authorization.authorize();
                case "new" -> {
                    allInfo = apiRequest.getNewReleases();
                    printPages(pageNumber, itemsPage, allInfo);
                }
                case "featured" -> {
                    allInfo = apiRequest.getFeatured();
                    printPages(pageNumber, itemsPage, allInfo);
                }
                case "categories" -> {
                    allInfo = apiRequest.getCategories();
                    printPages(pageNumber, itemsPage, allInfo);
                }
                case "playlists" -> {
                    String category = inputs[1];
                    allInfo = apiRequest.getPlaylists(category);
                    if (!allInfo.isEmpty()) {
                        printPages(pageNumber, itemsPage, allInfo);
                    }
                }
                case "next" -> {
                    pageNumber++;
                    if (!printPages(pageNumber, itemsPage, allInfo)) {
                        pageNumber--;
                    }
                }
                case "prev" -> {
                    pageNumber--;
                    if (!printPages(pageNumber, itemsPage, allInfo)) {
                        pageNumber++;
                    }
                }
                case "exit" -> {
                    if (allInfo.isEmpty()) {
                        System.exit(0);
                    } else {
                        allInfo.clear();
                    }
                }
                default -> System.out.println("wrong input");
            }
        }
    }
}
