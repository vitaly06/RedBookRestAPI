package ru.talisman.RedBook.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.talisman.RedBook.Models.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin> {
    @Override
    public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
        Admin admin = new Admin();
        try{
            admin.setId(resultSet.getInt("id"));
            admin.setLogin(resultSet.getString("login"));
            admin.setPassword(resultSet.getString("password"));
            admin.setFio(resultSet.getString("fio"));
        } catch (Exception e){
            return new Admin();
        }
        return admin;
    }
}
