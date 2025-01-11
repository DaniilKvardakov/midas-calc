package midas.models;


import midas.annotations.NoCommentsNeeded;

/**
 * Перечисление статусов ответов.
 */
@NoCommentsNeeded
public enum Status {
    SUCCESS("Успешный подсчет."),
    INVALID_NICKNAME_ERROR("Неверный никнейм!"),
    CLOSE_DOTA_PROFILE_ERROR("Закрыта история матчей в Dota! Сделайте матчи в настройках Dota общедоступными!"),
    DOTA_API_ERROR("Непредвиденная ошибка! Повторите попытку позже..."),
    DEFECTIVE_DOTA_MATCH("Матч либо очень старый, либо поврежден на стороне VALVE!"),
    CRITICAL_FAILURE("Критический сбой... Мы уже решаем проблему!"),
    NEED_FRIEND_CODE_ERROR("Повторите запрос, добавив код дружбы.");


    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}