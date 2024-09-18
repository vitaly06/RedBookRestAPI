package ru.talisman.RedBook.Mappers;


import org.springframework.jdbc.core.RowMapper;
import ru.talisman.RedBook.Models.Application;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet resultSet, int i) throws SQLException {
        Application application = new Application();
        try{
            application.setId(resultSet.getInt("id"));
            application.setHeader(resultSet.getString("header"));
            application.setPhoto(resultSet.getBytes("photo"));
            application.setDescription(resultSet.getString("description"));
            application.setArea(resultSet.getInt("area"));
            application.setIsAccepted("isAccepted");
        }
        catch (Exception e){
            System.out.println("Exception => " + e.getMessage());
            return new Application();
        }
        return application;
    }
}
