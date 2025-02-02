package nextstep.courses.domain;

public enum SessionStatus {

    PREPARING,
    RECRUITING,
    FINISHED;

    public static boolean isRecruiting(SessionStatus status) {
        return status == RECRUITING;
    }

    public boolean isSame(SessionStatus status) {
        return this == status;
    }
}
