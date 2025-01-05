package midas.util;

import midas.model.MidasData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
@PropertySource("classpath:midas.properties")
public class CalcFarmUtil {

    @Value("${midas.cooldown}")
    private int cooldown;
    @Value("${midas.sale.price}")
    private int salePrice;
    @Value("${midas.given.money}")
    private int givenMoney;
    @Value("${dota.api.uri}")
    private String dotaApiUri;

    public int getFarmByMidasData(MidasData midasData) {
        if (midasData.getTimeOfSellMidas() == null)
            midasData.setTimeOfSellMidas(midasData.getTotalTimeOfMatch());

        int result = (int) ((midasData.getTotalTimeOfMatch().minus(midasData.getMidasTime()).getSeconds() / cooldown) * givenMoney);

        if (midasData.getTotalTimeOfMatch().equals(midasData.getTimeOfSellMidas()))
            result += salePrice;

        if (midasData.getFailMidasCast() > 0)
            result -= givenMoney * midasData.getFailMidasCast();

        return result;

    }

    public int getFarmById() throws IOException {
        Document dotabuffPage = Jsoup.parse(new URL("https://ru.dotabuff.com/matches/8113747033/builds"), 3000);
        Elements elements = dotabuffPage.select("header[class=header no-padding]");
        Element necessaryElement;
        for (Element element : elements) {
            System.out.println(element);
            if (element.toString().contains("<a href=\"/items/power") && element.toString().contains("mode:sasal/bolt"))
                necessaryElement = element;
        }
        return 1;
    }

}
