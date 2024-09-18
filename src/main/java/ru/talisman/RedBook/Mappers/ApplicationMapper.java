package ru.talisman.RedBook.Mappers;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mock.web.MockMultipartFile;
import ru.talisman.RedBook.Models.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet resultSet, int i) throws SQLException {
        Application application = new Application();
        String fileName = resultSet.getString("photo");
        try {
            File file = new File("./src/main/resources/static/data/" + fileName);
            // Проверяем, существует ли файл
            if (!file.exists()) {
                throw new IOException("Файл не найден: " + file.getAbsolutePath());
            }
            try (FileInputStream input = new FileInputStream(file)) {
                application.setPhoto(new MockMultipartFile("file", file.getName(),
                        "multipart/form-data", IOUtils.toByteArray(input)));
            } catch (IOException e) {
                // Обработка исключений
                throw new IOException("Ошибка при чтении файла: " + file.getAbsolutePath(), e);
            }
        } catch (Exception e) {
            System.out.println("Exception 1 => " + e.getLocalizedMessage());
        }
        try{
            application.setId(resultSet.getInt("id"));
            application.setHeader(resultSet.getString("header"));
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
