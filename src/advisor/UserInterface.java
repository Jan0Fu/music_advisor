package advisor;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scan;

    public UserInterface() {
        this.scan = new Scanner(System.in);
    }

    public void start() {

        while (true) {
            String userInput = scan.nextLine();
            switch (userInput) {
                case "new" -> System.out.println(newReleases());
                case "featured" -> System.out.println(featured());
                case "categories" -> System.out.println(categories());
                case "playlists Mood" -> System.out.println(playlists());
                case "exit" -> exit();
                default -> System.out.println("wrong input");
            }
        }
    }

    private String newReleases() {
        System.out.println("---NEW RELEASES---");
        return new StringBuilder()
                .append("Mountains [Sia, Diplo, Labrinth]\n")
                .append("Runaway [Lil Peep]\n")
                .append("The Greatest Show [Panic! At The Disco]\n")
                .append("All Out Life [Slipknot]")
                .toString();
    }

    private String featured() {
        System.out.println("---FEATURED---");
        return new StringBuilder()
                .append("Mellow Morning\n")
                .append("Wake Up and Smell the Coffee\n")
                .append("Monday Motivation\n")
                .append("Songs to Sing in the Shower")
                .toString();
    }

    private String categories() {
        System.out.println("---CATEGORIES---");
        return "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin";
    }

    private String playlists() {
        System.out.println("---MOOD PLAYLISTS---");
        return "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll";
    }

    private void exit() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
}
