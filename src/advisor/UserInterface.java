package advisor;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scan;
    private boolean isAuthenticated;

    public UserInterface() {
        this.scan = new Scanner(System.in);
        this.isAuthenticated = false;
    }

    public void start() {

        while (true) {
            String userInput = scan.nextLine();
            switch (userInput) {
                case "auth" -> authentication();
                case "new" -> newReleases();
                case "featured" -> featured();
                case "categories" -> categories();
                case "playlists Mood" -> playlists();
                case "exit" -> exit();
                default -> System.out.println("wrong input");
            }
        }
    }

    private void authentication() {
        System.out.println("https://accounts.spotify.com/authorize?client_id=d7792bd713af4b8a8f100cf307a7c8bd&redirect_uri=https://www.example.com&response_type=code");
        isAuthenticated = true;
        System.out.println("---SUCCESS---");
    }

    private void newReleases() {
        if (isAuthenticated) {
            System.out.println("---NEW RELEASES---");
            System.out.println(new StringBuilder()
                    .append("Mountains [Sia, Diplo, Labrinth]\n")
                    .append("Runaway [Lil Peep]\n")
                    .append("The Greatest Show [Panic! At The Disco]\n")
                    .append("All Out Life [Slipknot]"));
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void featured() {
        if (isAuthenticated) {
            System.out.println("---FEATURED---");
            System.out.println(new StringBuilder()
                    .append("Mellow Morning\n")
                    .append("Wake Up and Smell the Coffee\n")
                    .append("Monday Motivation\n")
                    .append("Songs to Sing in the Shower"));
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void categories() {
        if (isAuthenticated) {
            System.out.println("---CATEGORIES---");
            System.out.println("Top Lists\n" +
                    "Pop\n" +
                    "Mood\n" +
                    "Latin");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void playlists() {
        if (isAuthenticated) {
            System.out.println("---MOOD PLAYLISTS---");
            System.out.println("Walk Like A Badass  \n" +
                    "Rage Beats  \n" +
                    "Arab Mood Booster  \n" +
                    "Sunday Stroll");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void exit() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
}
