package com.hex.arch.greeting.data.adapter.outbound;

import com.hex.arch.greeting.domain.model.Recipient;
import com.hex.arch.greeting.domain.repository.RecipientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RecipientJdbcRepository implements RecipientRepository {
    private final JdbcClient jdbcClient;

    public RecipientJdbcRepository(JdbcClient jdbcClient) {       // implements our second driven port ,sql code for recipients
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Recipient recipient) {
        jdbcClient
                .sql("""
                        INSERT INTO recipients (id, first_name, last_name, created_at, address, gender, age)
                        VALUES (:id, :firstName, :lastName, :createdAt, :address, :gender, :age)
                        ON CONFLICT (id) DO UPDATE SET
                            first_name = EXCLUDED.first_name,
                            last_name = EXCLUDED.last_name,
                            address = EXCLUDED.address,
                            gender = EXCLUDED.gender,
                            age = EXCLUDED.age
                        """)
                .param("id", recipient.id())
                .param("firstName", recipient.firstName())
                .param("lastName", recipient.lastName())
                .param("createdAt", java.sql.Timestamp.from(recipient.createdAt()))
                .param("address", recipient.address())
                .param("gender", recipient.gender())
                .param("age", recipient.age())
                .update();
    }

    @Override
    public Optional<Recipient> findById(UUID id) {
        return jdbcClient
                .sql("SELECT id, first_name, last_name, created_at, address, gender, age FROM recipients WHERE id = :id")
                .param("id", id)
                .query(new RowMapper<Recipient>() {
                    @Override
                    public Recipient mapRow(ResultSet rs, int rowNum) throws SQLException {   //anonymous class
                        return new Recipient(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getTimestamp("created_at").toInstant(),
                                rs.getString("address"),
                                rs.getString("gender"),
                                rs.getInt("age")
                        );
                    }
                })
                .optional();
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient
                .sql("DELETE FROM recipients WHERE id = :id")
                .param("id", id)
                .update();
    }
}