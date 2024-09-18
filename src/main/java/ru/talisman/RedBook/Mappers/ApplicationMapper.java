package ru.talisman.RedBook.Mappers;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.talisman.RedBook.Models.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {
    @Override
    public Application mapRow(ResultSet resultSet, int i) throws SQLException {
        Application application = new Application();
        String fileName = resultSet.getString("photo");
        try {
            // C:\Users\User\Desktop\Проекты Виталя\хакатоны\RedBook\src\main\resources\static\data
            //./src/main/resources/static/data/
            /*File file = new File("./src/main/resources/static/data/" + fileName);
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/png",
                    IOUtils.toByteArray(input));
            application.setPhoto(multipartFile);*/
            /*File file = new File("./src/main/resources/static/data/" + fileName);
            DiskFileItem fileItem = new DiskFileItem("file", "image/png", false, file.getName(),
                    (int) file.length(), file.getParentFile());
            fileItem.getOutputStream();
            MultipartFile multipartFile = new MultipartFile(fileItem);
            application.setPhoto(multipartFile);*/
            Path path = Paths.get("./src/main/resources/static/data/" + fileName);
            String originalFileName = fileName;
            String contentType = "text/plain";
            byte[] content = null;
            try {
                content = Files.readAllBytes(path);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            MultipartFile result = new MockMultipartFile(fileName,
                    originalFileName, contentType, content);
            application.setPhoto(result);
        } catch (Exception e) {
            System.out.println("Exception 1 => " + e.getLocalizedMessage());
        }
        try{
            application.setId(resultSet.getInt("id"));
            application.setHeader(resultSet.getString("header"));
            application.setDescription(resultSet.getString("description"));
        }
        catch (Exception e){
            System.out.println("Exception => " + e.getMessage());
            return new Application();
        }
        return application;
    }
}
