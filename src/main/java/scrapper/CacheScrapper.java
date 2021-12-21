package scrapper;

import lombok.SneakyThrows;

import java.sql.*;

public class CacheScrapper implements Scrapper {
    @Override @SneakyThrows
    public Home parse(String url) {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(String.format("select count(*) as count from homes where url='%s'", url));
        int count = rs.getInt("count");
        Home home;
        if (count == 0) {
            DefaultScrapper scrapper = new DefaultScrapper();
            home = scrapper.parse(url);
            String toInsert = String.format("insert into homes(url, price, beds, baths, garage) values('%s', '%d', '%f', '%f', '%f')", url, home.getPrice(), home.getBeds(), home.getBaths(), home.getGarages());
            statement.executeUpdate(toInsert);
        } else {
            String selector = String.format("select * from homes where url='%s'", url);
            rs = statement.executeQuery(selector);
            home = new Home(rs.getInt("price"), Double.parseDouble(rs.getString("beds").replaceAll(",", ".")), Double.parseDouble(rs.getString("baths").replaceAll(",", ".")), Double.parseDouble(rs.getString("garage").replaceAll(",", ".")));
        }
        return home;
    }
}