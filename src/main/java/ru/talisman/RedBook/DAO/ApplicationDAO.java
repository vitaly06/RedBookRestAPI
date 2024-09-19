package ru.talisman.RedBook.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.talisman.RedBook.Mappers.ApplicationMapper;
import ru.talisman.RedBook.Models.Application;

import java.util.List;

@Component
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        jdbcTemplate.update("INSERT INTO applications(header, photo, description, isAccepted, area, cadastral)" +
                        " VALUES (?, ?, ?, ?, ?, ?)",
                application.getHeader(), application.getPhoto(), application.getDescription(),
                application.getIsAccepted(), application.getArea(), application.getCadastral());
    }

    public List<Application> getApplications() {
        return jdbcTemplate.query("SELECT * FROM applications WHERE isAccepted = 'false'",
                new ApplicationMapper());
    }

    public List<Application> getAreaAnimals(int num){
        return jdbcTemplate.query("SELECT * FROM applications WHERE area = ? AND isAccepted = 'true'",
                new Object[]{num}, new ApplicationMapper());
    }

    public void updateApplication(int id) {
        jdbcTemplate.update("UPDATE applications SET isAccepted = 'true' WHERE id = ?", id);
    }
}
