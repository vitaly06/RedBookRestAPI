package ru.talisman.RedBook.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.talisman.RedBook.Mappers.AdminMapper;
import ru.talisman.RedBook.Models.Admin;
import ru.talisman.RedBook.Utill.AdminNotFoundException;

@Component
public class AdminDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdminDAO(){
        jdbcTemplate = null;
    }

    public Admin getAdmin(String login, String password) {
        return jdbcTemplate.query("SELECT * FROM admins WHERE login = ? AND password = ?",
                new Object[]{login, password}, new AdminMapper())
        .stream().findAny().orElseThrow(AdminNotFoundException::new);
    }
}
