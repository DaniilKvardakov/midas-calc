package midas.util;

import lombok.extern.slf4j.Slf4j;
import midas.model.MidasData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

@Component
@Slf4j
@PropertySource("classpath:midas.properties")
public class CalcFarmUtil {

    @Value("${midas.cooldown}")
    private int cooldown = 110;
    @Value("${midas.sale.price}")
    private int salePrice = 1100;
    @Value("${midas.given.money}")
    private int givenMoney = 160;
    @Value("${midas.dotabuff.url.first}")
    private String firstHalfSecond = "https://ru.dotabuff.com/matches/";
    @Value("${midas.dotabuff.url.second}")
    private String secondHalfURL = "/builds";

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

    public int getFarmById(String nick, long matchId) throws IOException {

        Document dotabuffPage = Jsoup.parse(new URL(firstHalfSecond + matchId + secondHalfURL), 3000);
        Elements elements = dotabuffPage.select("header[class=header no-padding]");
        Element necessaryMidasElement = null;

        for (Element element : elements) {

            if (element.toString().contains("<a href=\"/items/hand-of-midas") && element.toString().contains(nick))
                necessaryMidasElement = element;

        }

        String[] midasTimeByRawData = (
                (Element) (
                        necessaryMidasElement
                                .select("a[href=/items/hand-of-midas]")
                                .getFirst()
                                .parent()
                )
                        .nextSibling()
        )
                .text()
                .split(":");



        String[] totalTimeByRawData = dotabuffPage
                .select("span[class=duration]")
                .getFirst()
                .text()
                .split(":");


        Duration midasTime = Duration.ofMinutes(Long.parseLong(midasTimeByRawData[0])).plusSeconds(Long.parseLong(midasTimeByRawData[1]));
        Duration totalTime = Duration.ofMinutes(Long.parseLong(totalTimeByRawData[0])).plusSeconds(Long.parseLong(totalTimeByRawData[1]));

        log.info(String.valueOf(totalTime));
        log.info(String.valueOf(midasTime));

        return getFarmByMidasData(new MidasData(totalTime, midasTime));

    }

}
