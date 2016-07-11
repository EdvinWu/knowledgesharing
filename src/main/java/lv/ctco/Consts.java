package lv.ctco;

import org.springframework.http.HttpStatus;

public class Consts {
    public static final String SESSION_PATH = "/session";
    public static final String FEEDBACK_PATH = "/feedback";
    public static final String JSON = "application/json";
    public static final String SCHEDULED_PATH = "/scheduled";
    public static final String FALLING_ID = "/-1";
    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
}
