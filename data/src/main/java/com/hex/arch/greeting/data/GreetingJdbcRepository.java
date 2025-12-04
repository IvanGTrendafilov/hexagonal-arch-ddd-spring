package com.hex.arch.greeting.data;

import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.repository.GreetingRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NullMarked;
import org.springframework.jdbc.core.JdbcClient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@NullMarked
@Repository
public class GreetingJdbcRepository implements GreetingRepository {
    private final JdbcClient jdbcClient;

    public GreetingJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Greeting greeting) {
        jdbcClient
            .sql(
                """
                INSERT INTO greetings (id, message, recipient, created_at)
                VALUES (:id, :message, :recipient, :createdAt)
                ON CONFLICT (id) DO UPDATE SET
                    message = EXCLUDED.message,
                    recipient = EXCLUDED.recipient
                """
            )
            .param("id", greeting.id())
            .param("message", greeting.message())
            .param("recipient", greeting.recipient())
            .param("createdAt", greeting.createdAt())
            .update();
    }

    @Override
    public Optional<Greeting> findById(UUID id) {
        return jdbcClient
            .sql("SELECT id, message, recipient, created_at FROM greetings WHERE id = :id")
            .param("id", id)
            .query(new GreetingRowMapper())
            .optional();
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient.sql("DELETE FROM greetings WHERE id = :id").param("id", id).update();
    }

    private static class GreetingRowMapper implements RowMapper<Greeting> {
        @Override
        public Greeting mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Greeting(
                UUID.fromString(rs.getString("id")),
                rs.getString("message"),
                rs.getString("recipient"),
                rs.getTimestamp("created_at").toInstant()
            );
        }
    }
}

