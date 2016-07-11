package lv.ctco;

import org.springframework.http.HttpStatus;

public class Consts {
    public static final String SESSION_PATH = "/session";
    public static final String JSON = "/session";
    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
}
