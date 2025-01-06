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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

@Component
@Slf4j
@PropertySource("classpath:midas.properties")
public class CalcFarmUtil {

    @Value("${midas.cooldown}")
    private int cooldown;
    @Value("${midas.sale.price}")
    private int salePrice;
    @Value("${midas.given.money}")
    private int givenMoney;
    @Value("${midas.dotabuff.url.first}")
    private String firstHalfSecond;
    @Value("${midas.dotabuff.url.second}")
    private String secondHalfURL;
    @Value("${midas.tag.team.table}")
    private String teamTable;
    @Value("${midas.icon.a}")
    private String icon;
    @Value("${midas.icon.class}")
    private String iconClass;
    @Value("${midas.duration.span}")
    private String span;

    public int getFarmByMidasData(MidasData midasData) {

        // Если не клиент не отправил время продажи мидаса, то будем считать что он был у него весь матч.
        if (midasData.getTimeOfSellMidas() == null)
            midasData.setTimeOfSellMidas(midasData.getTotalTimeOfMatch());

        // ( ( Время длительности матча - время покупки мидаса ) / кулдаун мидаса ) * награда с использования мидаса
        int result = (int) ((midasData.getTotalTimeOfMatch().minus(midasData.getMidasTime()).getSeconds() / cooldown) * givenMoney);

        // Если клиент отправил тайминг продажи, то к прибыли от мидаса прибавляем бабки от его продажи.
        if (!midasData.getTotalTimeOfMatch().equals(midasData.getTimeOfSellMidas())) result += salePrice;

        if (midasData.getFailMidasCast() > 0) result -= givenMoney * midasData.getFailMidasCast();

        return result;

    }

    public Integer getFarmById(String nick, long matchId) {

        System.out.println("nick: |" + nick + "|");
        System.out.println("matchId: |" + matchId + "|");

        Document dotabuffPage;
        try {
            URL dotabuffURL = new URL(firstHalfSecond + matchId + secondHalfURL);
            dotabuffPage = Jsoup.parse(dotabuffURL, 3000);
        } catch (IOException e) {
            System.out.println("URL error!");
            return null;
        }
        System.out.println("doc good!");

        Elements elements = dotabuffPage.select(teamTable); // Ищем "div" с референсом team_table.png
        Element necessaryMidasElement = null;

        for (Element element : elements) {
            // Если есть элемент c референсом icon_midas_a.png содержащий иконку мидаса и текст с именем игрока, то выкарчёвываем этот элемент.
            if (element.toString().contains(icon) && element.toString().contains(nick))
                necessaryMidasElement = element;
        }


        String[] midasTimeByRawData;
        try {

            midasTimeByRawData = ((Element) Objects.requireNonNull((Objects.requireNonNull(Objects.requireNonNull(necessaryMidasElement)
                    .select(iconClass) // Тут просто выражение для поиска ССЫЛКИ(тег <a>) на иконку мидаса.
                    .getFirst()
                    .parent()))
                    .nextSibling()))
                    .text()
                    .split(":");

        } catch (NullPointerException n) {
            System.out.println("Element not found!");
            n.printStackTrace();
            System.out.println(n.getMessage());
            return null;
        }


        String[] totalTimeByRawData = dotabuffPage
                .select(span) // Тут просто выражение для поиска span-а, где записана длительность матча. Референс - duration.png
                .getFirst()
                .text()
                .split(":");


        // Парсим нашего "уродца" в Duration.
        Duration midasTime = Duration.ofMinutes(Long.parseLong(midasTimeByRawData[0])).plusSeconds(Long.parseLong(midasTimeByRawData[1]));
        Duration totalTime = Duration.ofMinutes(Long.parseLong(totalTimeByRawData[0])).plusSeconds(Long.parseLong(totalTimeByRawData[1]));

        System.out.println(midasTime);
        System.out.println(totalTime);


        return getFarmByMidasData(new MidasData(totalTime, midasTime));

    }

}
