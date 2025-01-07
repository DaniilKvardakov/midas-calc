package midas.response;

public enum Status {
    SUCCESS("Успешный подсчет."),
    ERROR("Проверь ник!");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}