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
        jdbcTemplate.update("INSERT INTO applications(header, photo, description, isAccepted, area) VALUES (?, ?, ?, ?, ?)",
                application.getHeader(), application.getPhoto().getOriginalFilename(), application.getDescription(),
                application.getIsAccepted(), application.getArea());
    }

    public List<Application> getApplications() {
        return jdbcTemplate.query("SELECT * FROM applications", new ApplicationMapper());
    }
    public Application getApplication(int id) {
        return jdbcTemplate.query("SELECT * FROM applications WHERE id = ?", new Object[]{id}, new ApplicationMapper())
                .stream().findAny().orElse(null);
    }
}
