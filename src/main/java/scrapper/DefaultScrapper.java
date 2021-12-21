package scrapper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DefaultScrapper implements Scrapper{
    private final static String PRICE_SELECTOR = ".detail__info-xlrg";
    private final static String BEDS_SELECTOR = ".nhs_BedsInfo span";
    private final static String BATHS_SELECTOR = ".nhs_BathsInfo span";
    private final static String GARAGE_SELECTOR = ".nhs_GarageInfo span";

    @Override @SneakyThrows
    public Home parse(String url) {
        Document doc = Jsoup.connect(url).get();
        int price = getPrice(doc);
        double baths = getBaths(doc);
        double beds = getBeds(doc);
        double garages = getGarages(doc);
        return new Home(price, beds, baths, garages);
    }

    private static int getPrice(Document doc) {
        String price = doc.selectFirst(PRICE_SELECTOR).text().replaceAll("[^0-9]", "");
        return Integer.parseInt(price);
    }

    private static double getBeds(Document doc) {
        String bed = doc.selectFirst(BEDS_SELECTOR).text().replaceAll("[^0-9,]", "");
        return Double.parseDouble(bed);
    }

    private static double getBaths(Document doc) {
        String bath = doc.selectFirst(BATHS_SELECTOR).text().replaceAll("[^0-9,]", "");
        return Double.parseDouble(bath);
    }

    private static double getGarages(Document doc) {
        String garage = doc.selectFirst(GARAGE_SELECTOR).text().replaceAll("[^0-9,]", "");
        return Double.parseDouble(garage);
    }
}