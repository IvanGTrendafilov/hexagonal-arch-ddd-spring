package com.hex.arch.greeting.data.adapter.outbound;

import com.hex.arch.greeting.domain.model.Greeting;
import com.hex.arch.greeting.domain.model.Recipient;
import com.hex.arch.greeting.domain.driven.port.ForGreetingPersistence;
import com.hex.arch.greeting.domain.repository.RecipientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jspecify.annotations.NullMarked;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@NullMarked
@Repository
public class ForGreetingJdbcPersistence implements ForGreetingPersistence {   // implements our driven port , sql
                                                                              // code for greetings
    private final JdbcClient jdbcClient;
    private final RecipientRepository recipientRepository;

    public ForGreetingJdbcPersistence(JdbcClient jdbcClient, RecipientRepository recipientRepository) {
        this.jdbcClient = jdbcClient;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public void save(Greeting greeting) {
        if (greeting.recipient() != null) {
            recipientRepository.save(greeting.recipient());
        }

        jdbcClient
                .sql("""
                        INSERT INTO greetings (id, message, recipient_id, created_at)
                        VALUES (:id, :message, :recipientId, :createdAt)
                        ON CONFLICT (id) DO UPDATE SET
                            message = EXCLUDED.message,
                            recipient_id = EXCLUDED.recipient_id
                        """)
                .param("id", greeting.id())
                .param("message", greeting.message())
                .param("recipientId", greeting.recipient() != null ? greeting.recipient().id() : null)
                .param("createdAt", java.sql.Timestamp.from(greeting.createdAt()))
                .update();
    }

    @Override
    public Optional<Greeting> findById(UUID id) {
        return jdbcClient
                .sql("SELECT id, message, recipient_id, created_at FROM greetings WHERE id = :id")
                .param("id", id)
                .query(new GreetingRowMapper())
                .optional();
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient.sql("DELETE FROM greetings WHERE id = :id").param("id", id).update();
    }

    private class GreetingRowMapper implements RowMapper<Greeting> {
        @Override
        public Greeting mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID recipientId = rs.getString("recipient_id") != null
                    ? UUID.fromString(rs.getString("recipient_id"))
                    : null;

            Recipient recipient = null;
            if (recipientId != null) {
                recipient = recipientRepository.findById(recipientId).orElse(null);
            }

            return new Greeting(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("message"),
                    recipient,
                    rs.getTimestamp("created_at").toInstant()
            );
        }
    }

    @Override
    public List<Greeting> findAll() {
        return jdbcClient
                .sql("SELECT id, message, recipient_id, created_at FROM greetings")
                .query(new GreetingRowMapper())
                .list();
    }
}