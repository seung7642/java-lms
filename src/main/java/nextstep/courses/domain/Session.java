package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {

    protected final int maximumNumberOfStudent;
    protected final List<NsUser> students = new ArrayList<>();
    protected final LocalDateTime startedAt;
    protected final LocalDateTime endedAt;

    protected Session(int maximumNumberOfStudent, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.maximumNumberOfStudent = maximumNumberOfStudent;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public abstract void register(List<NsUser> users);
}
