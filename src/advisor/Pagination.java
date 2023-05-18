package advisor;

import java.util.List;

public class Pagination {

    public static boolean printPages(int pageNumber, int itemsPage, List<String> info) {
        int maxPages = info.size() % itemsPage == 0 ? info.size() / itemsPage : (info.size() / itemsPage) + 1;
        if ((pageNumber > maxPages) || (pageNumber <= 0)) {
            System.out.println("No more pages.");
            return false;
        }
        for (int i = (pageNumber - 1) * itemsPage; i < pageNumber * itemsPage; i++) {
            System.out.println(info.get(i));
        }
        System.out.printf("---PAGE %d OF %d---\n", pageNumber, maxPages);
        return true;
    }
}
