package advisor;

public class Main {
    public static void main(String[] args) {
        String path = null;

        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-access")) {
                    path = args[i + 1];
                }
            }
        } else {
            path = "https://accounts.spotify.com";
        }

        UserInterface ui = new UserInterface(path);
        ui.start();
    }
}
