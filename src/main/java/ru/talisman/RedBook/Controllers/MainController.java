package ru.talisman.RedBook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.talisman.RedBook.DAO.AdminDAO;
import ru.talisman.RedBook.DAO.ApplicationDAO;
import ru.talisman.RedBook.Models.Admin;
import ru.talisman.RedBook.Models.Application;
import ru.talisman.RedBook.Utill.AdminErrorResponse;
import ru.talisman.RedBook.Utill.AdminNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class MainController {
    @Autowired
    AdminDAO adminDAO;
    @Autowired
    ApplicationDAO applicationDAO;

    // Вход в панель Админа
    @PostMapping("/loginAdmin")
    public Admin loginAdmin(HttpServletRequest request,
        @RequestParam("login") String login, @RequestParam("password") String password) {
        Admin admin = adminDAO.getAdmin(login, password);
        return admin;
    }
    // Отправка заявки
    @PostMapping("/addApplication")
    public void addApplication(HttpServletRequest request,
                               @RequestParam("title") String header,
                               @RequestParam("image") MultipartFile photo,
                               @RequestParam("description") String description,
                               @RequestParam("selectedImage") int area){
        Application application = new Application();
        application.setIsAccepted("false");
        application.setHeader(header);
        application.setPhoto(photo);
        application.setDescription(description);
        application.setArea(area);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(application.getPhoto().getOriginalFilename()));
        try {
            Path path = Paths.get("./src/main/resources/static/data/" + fileName);
            Files.copy(application.getPhoto().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("exeption: " + e.getMessage());
        }
        applicationDAO.saveApplication(application);
    }

    @GetMapping("/getAllApplications")
    public List<Application> getAllApplications(){
            return applicationDAO.getApplications();
    }

    @GetMapping("/getApplication/{id}")
    public Application getAllApplication(@PathVariable int id){
        return applicationDAO.getApplication(id);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleException(AdminNotFoundException e){
        AdminErrorResponse response = new AdminErrorResponse(
                "Админа с такими данными не существует",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }
}
