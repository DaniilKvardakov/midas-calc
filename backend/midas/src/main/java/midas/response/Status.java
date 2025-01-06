package midas.response;

public enum Status {
    NO_ERRORS("Successful calculation."),
    DOTABUFF_URL_ERROR("Midas API deprecated!"),
    PARSE_ERROR("Check nickname!");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}