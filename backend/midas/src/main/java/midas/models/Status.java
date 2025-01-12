package midas.models;


import midas.annotations.NoCommentsNeeded;

/**
 * Перечисление статусов ответов.
 */
@NoCommentsNeeded
public enum Status {


    SUCCESS("Успешный подсчет."),
    INVALID_NICKNAME_ERROR("Неверный никнейм!"),
    DEFECTIVE_DOTA_MATCH("Матч либо очень старый, либо поврежден на стороне VALVE!"),
    NEED_FRIEND_CODE_ERROR("Обнаружено несколько игроков с введенным никнеймом! Повторите запрос, добавив код дружбы."),
    MATCH_NOT_ANALYZED_ERROR("Не получилось проанализировать матч. Повторите попытку позже..."),
    CHECK_STATUS_PARSING_ERROR("Критический сбой... Мы уже решаем проблему!"),
    REPORT_BUG_ERROR("Критический сбой... Повторите попытку позже..."),
    NO_MIDAS_ERROR("У игрока с введенным предметом не было мидаса!"),
    MATCH_NOT_FOUND_OR_MATCH_ID_IS_INVALID_ERROR("Номер матча недействителен или не найден!");




    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}