package midas.util;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import midas.annotations.Commented;
import midas.models.Match;
import midas.models.Player;
import midas.models.PurchaseLog;
import midas.models.Status;
import midas.models.MidasResponse;
import midas.models.SteamResponse;
import midas.models.SteamUserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Компонент для подсчета фарма мидаса..
 */
@Commented
@Component
@Slf4j
@PropertySource({"classpath:secret/midas.properties", "classpath:secret/dota_api.properties"})
public class CalcFarmUtil {

    /**
     * URL для запроса к dota open api.
     */
    @Value("${dota.api.url}")
    private String dotaApiUrl = "https://api.opendota.com/api/matches/";
    /**
     * Цена продажи мидаса.
     */
    @Value("${midas.sale.price}")
    private int salePrice = 1100;
    /**
     * Денег дается при использовании мидаса.
     */
    @Value("${midas.given.money}")
    private int givenMoney = 160;
    /**
     * Цена мидаса.
     */
    @Value("${midas.price}")
    private int midasPrice = 2200;
    /**
     * Название мидаса из JSON, который отправляет dota open api.
     */
    @Value("${midass.json.name}")
    private String midasJsonName = "hand_of_midas";
    /**
     * Константа для нахождения steam id.
     */
    @Value("${midas.steam.url.converter}")
    private long steam64base = 76561197960265728L;
    /**
     * Первая часть url для запроса к steam api. (Когда нужно получить имя игрока по account id)
     */
    @Value("${midas.steam.first.api.url}")
    private String firstHalfOfSteamURL;
    /**
     * Вторая часть url для запроса к steam api. (Когда нужно получить имя игрока по account id)
     */
    @Value("${midas.steam.second.api.url}")
    private String secondHalfOfSteamURL;
    /**
     * Ключ Steam API.
     */
    @Value("${midas.steam.api.key}")
    private String steamApiKey;
    /**
     * Тут просто константы пар статус ошибки + заглушка для "плохого ответа".
     * @see MidasResponse
     */
    private static final Map.Entry<Status, MidasResponse> DOTA_API_PROBLEM = new AbstractMap.SimpleEntry<>(Status.DOTA_API_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    private static final Map.Entry<Status, MidasResponse> DEFECTIVE_MATCH_PROBLEM = new AbstractMap.SimpleEntry<>(Status.DEFECTIVE_DOTA_MATCH, MidasResponse.BAD_MIDAS_RESPONSE);
    private static final Map.Entry<Status, MidasResponse> CLOSE_DOTA_PROBLEM = new AbstractMap.SimpleEntry<>(Status.CLOSE_DOTA_PROFILE_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    private static final Map.Entry<Status, MidasResponse> NICKNAME_PROBLEM = new AbstractMap.SimpleEntry<>(Status.INVALID_NICKNAME_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);
    private static final Map.Entry<Status, MidasResponse> CRITICAL_PROBLEM = new AbstractMap.SimpleEntry<>(Status.CRITICAL_FAILURE, MidasResponse.BAD_MIDAS_RESPONSE);
    private static final Map.Entry<Status, MidasResponse> NEED_CODE_PROBLEM = new AbstractMap.SimpleEntry<>(Status.NEED_FRIEND_CODE_ERROR, MidasResponse.BAD_MIDAS_RESPONSE);

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

        RestTemplate template = new RestTemplate();
        ResponseEntity<Match> response;

        // Если упал dota api или пришло хрен пойми что...
        try {
            response = template.getForEntity(dotaApiUrl + matchId, Match.class);
        } catch (Exception e) {
            System.err.println("Dota 2 API не отвечает по данному URL!");
            System.err.println("Message: " + e.getMessage());
            System.err.println("StackTrace: " + Arrays.toString(e.getStackTrace()));
            return DOTA_API_PROBLEM;
        }

        Match match = response.getBody();

        if (!response.hasBody())
            return DOTA_API_PROBLEM;

        if (match == null || match.getPlayers() == null || match.getPlayers().isEmpty())
            return DEFECTIVE_MATCH_PROBLEM;

        // Фильтруем игроков до нужного, сравнивая предоставленный никнейм с теми, которые получили из матча.
        List<Player> players = match.getPlayers().stream().filter(p -> {
                    String name = p.getPersonaName();
                    if (name != null)
                        return p.getPersonaName().equalsIgnoreCase(nick);
                    else {
                        if (p.getAccountId() != 0) {

                            // Если профиль закрыт и нельзя достать имя игрока из dota api, но есть account id, то дергаем имя из steam api:
                            SteamUserData steamUserData = template.getForEntity(
                                    firstHalfOfSteamURL + steamApiKey + secondHalfOfSteamURL + accountIdToSteam64(p.getAccountId()),
                                    SteamUserData.class
                            ).getBody();

                            SteamResponse steamResponse = null;

                            try {
                                if (steamUserData != null) {
                                    steamResponse = steamUserData.getSteamResponse();
                                }
                            } catch (NullPointerException n) {
                                return false;
                            }

                            if (steamResponse != null)
                                return steamResponse.getPlayers().getFirst().getPersonaName().equalsIgnoreCase(nick);

                            return false;
                        }
                        return false;
                    }
                }

        ).toList();


        if (players.isEmpty()) {
            System.err.println("Открой профиль у%б%%е...");
            return CLOSE_DOTA_PROBLEM;
        }

        Player player;
        // Если несколько игроков с одинаковым именем.
        // У всех уникальный код дружбы, поэтому конечный .get() без проверок.
        if (players.size() > 1) {

            if (friendCode == null)
                return NEED_CODE_PROBLEM;

            System.err.println("Несколько игроков с одинаковым именем!");
            player = players.stream().filter(p -> p.getAccountId() == friendCode).findFirst().get();
        } else
            player = players.getFirst();

        // Невероятная ситуация, но всё же обработаю.
        if (player == null) {
            try {
                reportABug("nick: : " + nick + "\nmatch id: " + matchId);
            } catch (MessagingException e) {
                System.out.println("Баг репорт не удался =(");
            }
            return CRITICAL_PROBLEM;
        }

        int midasUses = player.getItemUses().get(midasJsonName);
        Integer timeInSecondsOfByMidas;

        List<PurchaseLog> checkList = player
                .getPurchaseLogs()
                .stream()
                .filter(e -> e.getItemName().equals(midasJsonName))
                .toList();
        timeInSecondsOfByMidas = checkList.isEmpty() ? null : checkList.getFirst().getTime();

        if (timeInSecondsOfByMidas == null)
            return NICKNAME_PROBLEM;

        return new AbstractMap.SimpleEntry<>(Status.SUCCESS, new MidasResponse(
                midasUses,
                (long) midasUses * givenMoney,
                ((long) midasUses * givenMoney) + salePrice,
                getTimeOfPayback(Duration.ofSeconds(timeInSecondsOfByMidas)).getSeconds() / 60,
                (midasUses * givenMoney) > midasPrice
        )
        );
    }

    /**
     * Метод считающий время, когда мидас окупился на основе времени его покупки.
     *
     * @param midasTime время покупки мидаса.
     * @return время когда мидас окупился.
     * @see Duration
     */
    public Duration getTimeOfPayback(Duration midasTime) {
        int priceBuffer = 0;
        Duration durationBuffer = midasTime;
        while (priceBuffer < midasPrice) {
            durationBuffer = durationBuffer.plus(Duration.ofSeconds(110));
            priceBuffer += 160;
        }
        return durationBuffer;
    }

    public long accountIdToSteam64(long accountId) {
        return steam64base + accountId;
    }

    /**
     * Специальный пароль от почты почты клиента.
     */
    @Value("${midas.bag.report.secret.key}")
    private String reportKey;
    /**
     * Адрес отправителя(приложения).
     */
    @Value("${midas.bag.report.from}")
    private String reportFrom;
    /**
     * Адрес получателя(разработчик).
     */
    @Value("${midas.bag.report.to}")
    private String reportTo;

    /**
     * Метод отправки баг репорта на почту.
     * @param reportData данные при которых возникла ошибка.
     * @throws MessagingException
     * @see MessagingException
     */
    public void reportABug(String reportData) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(reportFrom));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reportTo));
        msg.setSubject("Problem Midas!");
        msg.setText(reportData);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.mail.ru", reportFrom, reportKey); // Явное подключение
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

}
