package midas.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import midas.annotations.Commented;
import midas.infrastructure.OpenDotaAPIClient;
import midas.infrastructure.SteamAPIClient;
import midas.models.Match;
import midas.models.ParsingProcess;
import midas.models.Player;
import midas.models.PurchaseLog;
import midas.models.Status;
import midas.models.MidasResponse;
import midas.models.SteamResponse;
import midas.models.SteamUserData;
import midas.components.Reporter;
import midas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Компонент для подсчета фарма мидаса...
 */
@Commented
@Service
@Slf4j
@PropertySource({"classpath:secret/midas.properties", "classpath:secret/dota_api.properties"})
public class MidasService {

    /**
     * Цена продажи мидаса.
     */
    @Value("${midas.sale.price}")
    private int salePrice;
    /**
     * Денег дается при использовании мидаса.
     */
    @Value("${midas.given.money}")
    private int givenMoney;
    /**
     * Цена мидаса.
     */
    @Value("${midas.price}")
    private int midasPrice;
    /**
     * Название мидаса в JSON-е Dota Open API.
     */
    @Value("${midass.json.name}")
    private String midasJsonName;

    private final OpenDotaAPIClient openDotaAPIClient;
    private final SteamAPIClient steamAPIClient;
    private final Reporter reporter;

    @Autowired
    public MidasService(OpenDotaAPIClient openDotaAPIClient, SteamAPIClient steamAPIClient, Reporter reporter) {
        this.openDotaAPIClient = openDotaAPIClient;
        this.steamAPIClient = steamAPIClient;
        this.reporter = reporter;
    }

    /**
     * Метод возвращающий карту ответа, используя dota open api.
     *
     * @param matchId    индефикатор матча.
     * @param nick       ник игрока.
     * @param friendCode код дружбы steam.
     * @return карта ответа.
     * @see Status
     * @see MidasResponse
     * @see Match
     * @see Player
     * @see PurchaseLog
     */
    public Map.Entry<Status, MidasResponse> getFarmByParseMatch(long matchId, String nick, Long friendCode) {

        // Отправляем запрос на парсинг.
        ParsingProcess parsingProcess = openDotaAPIClient.getParsingProcessById(matchId);

        // Проверяем что запрос принят.
        if (parsingProcess == null)
            return ResponseUtil.MATCH_NOT_ANALYZED_PROBLEM;

        // Отправляем запрос на матч.
        Match match = openDotaAPIClient.getMatchById(matchId);

        // Проверяем что запрос принят.
        if (match == null)
            return ResponseUtil.MATCH_NOT_FOUND_OR_MATCH_ID_IS_INVALID_PROBLEM;

        // Минуту проверяем матч на то пропарсен ли он или нет.
        int counterForError = 0;
        while (!match.isParsed()) {
            System.out.println("Не обработанный матч! Старт обработки...");
            if (counterForError >= 30)
                return ResponseUtil.MATCH_NOT_ANALYZED_PROBLEM;
            counterForError++;
            match = openDotaAPIClient.getMatchById(matchId);
            System.out.println("parse status -> " + Objects.requireNonNull(match).isParsed());
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                try {
                    reporter.reportABug("match id:" + matchId + "\nnick: " + nick + "\nfriend code: " + friendCode);
                } catch (MessagingException ex) {
                    return ResponseUtil.REPORT_BUG_PROBLEM;
                }
                return ResponseUtil.CHECK_STATUS_PARSING_PROBLEM;
            }
        }

        // Проверим матч на дефекты.
        if (match.getPlayers() == null || match.getPlayers().isEmpty())
            return ResponseUtil.DEFECTIVE_MATCH_PROBLEM;

        // Фильтруем игроков до нужного, сравнивая предоставленный никнейм с теми, которые получили из матча.
        List<Player> players = match.getPlayers().stream().filter(p -> {

                    System.out.println("\nplayer: " + p + "\n");

                    String name = p.getPersonaName();
                    if (name != null)
                        return name.equalsIgnoreCase(nick);
                    else {

                        System.err.println("Обнаружены игроки с недостающими данными!");

                        if (p.getAccountId() != 0) {

                            // Если есть недостающие данные и нельзя достать имя игрока из dota api, но есть account id, то дергаем имя из steam api:
                            SteamUserData steamUserData = steamAPIClient.getSteamUserData(p.getAccountId());
                            SteamResponse steamResponse;

                            // Пытаюсь дернуть SteamResponse:
                            try {
                                if (steamUserData != null)
                                    steamResponse = steamUserData.getSteamResponse();
                                else
                                    return false;
                            } catch (NullPointerException n) {
                                return false;
                            }

                            // Если получилось дернуть, сверяю пришедший никнейм от клиента с тем, что получил от Steam-а.
                            if (steamResponse != null) {
                                String nameBySteam = steamResponse.getPlayers().getFirst().getPersonaName();
                                System.out.println("Никнейм игрока " + nameBySteam + " обнаружен с помощью Steam API!");
                                System.out.println("nameBySteam.equalsIgnoreCase(nick): " + nameBySteam.equalsIgnoreCase(nick));
                                return nameBySteam.equalsIgnoreCase(nick);
                            }
                            return false;

                        }

                        return false;

                    }

                }
        ).toList();

        if (players.isEmpty()) {
            System.err.println("Некорректный никнейм!");
            return ResponseUtil.NICKNAME_PROBLEM;
        }

        Player player;
        // Если несколько игроков с одинаковым именем, то сравниваю игроков по коду дружбы.
        if (players.size() > 1) {

            if (friendCode == null) {
                System.err.println("Несколько игроков с одинаковым именем!");
                return ResponseUtil.NEED_CODE_PROBLEM;
            } else {
                // У всех уникальный код дружбы, поэтому конечный .get() без проверок.
                player = players.stream().filter(p -> p.getAccountId() == friendCode).findFirst().get();
            }

        } else
            player = players.getFirst();

        // Достаю касты предметов у игрока.
        Map<String, Integer> itemUses = player.getItemUses();

        // Ищу касты конкретно мидаса.
        Integer midasUses = itemUses.get(midasJsonName);

        // Время покупки мидаса в секундах.
        Integer timeInSecondsOfByMidas;

        //  Дружок, а у тебя вообще мидас то был?
        List<PurchaseLog> checkList = player
                .getPurchaseLogs()
                .stream()
                .filter(e -> e.getItemName().equals(midasJsonName))
                .toList();

        timeInSecondsOfByMidas = checkList.isEmpty() ? null : checkList.getFirst().getTime();

        // Мидас не входил в сделку...
        if (timeInSecondsOfByMidas == null)
            return ResponseUtil.NO_MIDAS_PROBLEM;

        // После многочисленных проверок формирую ответ:
        return new AbstractMap.SimpleEntry<>(Status.SUCCESS, new MidasResponse(
                midasUses,
                (long) midasUses * givenMoney,
                ((long) midasUses * givenMoney) + salePrice,
                MidasResponse.getTimeOfPayback(Duration.ofSeconds(timeInSecondsOfByMidas), midasPrice).getSeconds() / 60,
                (midasUses * givenMoney) > midasPrice
        )
        );
    }

}
