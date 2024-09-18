package ru.talisman.RedBook.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.talisman.RedBook.Mappers.ApplicationMapper;
import ru.talisman.RedBook.Models.Application;

import java.util.List;

@Component
public class ApplicationDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApplicationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApplicationDAO() {
        jdbcTemplate = null;
    }

    public void saveApplication(Application application) {
        assert jdbcTemplate != null;
        jdbcTemplate.update("INSERT INTO applications(header, photo, description) VALUES (?, ?, ?)",
                application.getHeader(), application.getPhoto().getOriginalFilename(), application.getDescription());
    }

    public List<Application> getApplications() {
        assert jdbcTemplate != null;
        return jdbcTemplate.query("SELECT * FROM applications", new ApplicationMapper());
    }
}
