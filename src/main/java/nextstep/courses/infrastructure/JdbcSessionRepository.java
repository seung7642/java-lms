package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (id, course_id, maximum_number_of_student, started_at, ended_at, status, progress_status, recruitment_status, " +
                "amount, type) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                session.getId(),
                session.getCourseId(),
                session.getMaximumNumberOfStudent(),
                session.getStartedAt(),
                session.getEndedAt(),
                session.getStatus().toString(),
                session.getProgressStatus().toString(),
                session.getRecruitmentStatus().toString(),
                session.getAmount(),
                session.getType().toString()
        );
    }

    @Override
    public Session findById(Long id) {
        try {
            String sql = "select id, course_id, maximum_number_of_student, started_at, ended_at, status, amount, type, " +
                    "from session where id = ?";

            RowMapper<Session> rowMapper = (rs, rowNum) -> {
                long sessionId = rs.getLong(1);
                String type = rs.getString(7);

                if (type.equals("FREE")) {
                    return new FreeSession(
                            sessionId,
                            rs.getLong(2),
                            toLocalDateTime(rs.getTimestamp(4)),
                            toLocalDateTime(rs.getTimestamp(5))
                    );
                }

                return new PaidSession(
                        sessionId,
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getLong(7),
                        toLocalDateTime(rs.getTimestamp(4)),
                        toLocalDateTime(rs.getTimestamp(5))
                );
            };
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
