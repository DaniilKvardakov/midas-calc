package midas.util;

import lombok.extern.slf4j.Slf4j;
import midas.response.Status;
import midas.model.MidasData;
import midas.response.MidasResponse;
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
import java.time.Duration;
import java.util.Map;
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
    @Value("${midas.dotabuff.timeout}")
    private int timeout;
    @Value("${midas.price}")
    private int midasPrice;

    public Map<Status, MidasResponse> getFarmByMidasData(MidasData midasData) {

        // Если не клиент не отправил время продажи мидаса, то будем считать что он был у него весь матч.
        if (midasData.getTimeOfSellMidas() == null)
            midasData.setTimeOfSellMidas(midasData.getTotalTimeOfMatch());

        // ( ( Время длительности матча - время покупки мидаса - потерянное время ) / кулдаун мидаса ) * награда с использования мидаса
        int numOfUses = (int)(midasData.getTotalTimeOfMatch().minus(midasData.getMidasTime()).minus(midasData.getWastedTime()).getSeconds() / cooldown);
        int result = numOfUses * givenMoney;
        int resultBufferBeforeSale = result;

        // Подсчет тайминга окупа.
        int priceBuffer = 0;
        Duration durationBuffer = Duration.ZERO;
        while (priceBuffer < midasPrice) {
            durationBuffer = durationBuffer.plus(Duration.ofSeconds(110));
            priceBuffer += 160;
        }

        // Если клиент отправил тайминг продажи, то к прибыли от мидаса прибавляем бабки от его продажи.
        if (!midasData.getTotalTimeOfMatch().equals(midasData.getTimeOfSellMidas()))
            result += salePrice;

        return Map.of(Status.SUCCESS, new MidasResponse(resultBufferBeforeSale, result == resultBufferBeforeSale ? 0 : result, durationBuffer.getSeconds() / 60));

    }

    public Map<Status, MidasResponse> getFarmById(String nick, long matchId) {

        Document dotabuffPage;
        try {
            URI dotabuffURL = new URI(firstHalfSecond + matchId + secondHalfURL);
            dotabuffPage = Jsoup.parse(dotabuffURL.toURL(), timeout);
        } catch (IOException | URISyntaxException e) {
            return Map.of(Status.ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
        }

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
            return Map.of(Status.ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
        }


        String[] totalTimeByRawData = dotabuffPage
                .select(span) // Тут просто выражение для поиска span-а, где записана длительность матча. Референс - duration.png
                .getFirst()
                .text()
                .split(":");


        // Парсим нашего "уродца" в Duration.
        Duration midasTime = Duration.ofMinutes(Long.parseLong(midasTimeByRawData[0])).plusSeconds(Long.parseLong(midasTimeByRawData[1]));
        Duration totalTime = Duration.ofMinutes(Long.parseLong(totalTimeByRawData[0])).plusSeconds(Long.parseLong(totalTimeByRawData[1]));

        return getFarmByMidasData(new MidasData(totalTime, midasTime, totalTime, Duration.ZERO));

    }

}
