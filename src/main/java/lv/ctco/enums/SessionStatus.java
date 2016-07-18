package lv.ctco.enums;

public enum SessionStatus {
    PENDING,
    DONE,
    APPROVED;

    public static SessionStatus getByName(String status) {
       return  SessionStatus.valueOf(status.toUpperCase());
    }
}


