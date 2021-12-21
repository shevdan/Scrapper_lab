package scrapper;

public class Main {
    public static void main(String[] args) {

        String url = "https://www.newhomesource.com/plan/encore-wlh-taylor-morrison-austin-tx/1771471";

        CacheScrapper cacheScrapper = new CacheScrapper();
        Home home = cacheScrapper.parse(url);
        System.out.println(home);
    }
}