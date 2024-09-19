package ru.talisman.RedBook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.talisman.RedBook.DAO.AdminDAO;
import ru.talisman.RedBook.DAO.ApplicationDAO;
import ru.talisman.RedBook.Models.Admin;
import ru.talisman.RedBook.Models.Application;
import ru.talisman.RedBook.Utill.AdminErrorResponse;
import ru.talisman.RedBook.Utill.AdminNotFoundException;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {
    @Autowired
    AdminDAO adminDAO;
    @Autowired
    ApplicationDAO applicationDAO;

    // Вход в панель Админа
    @PostMapping("/loginAdmin")
    public String loginAdmin(HttpServletRequest request,
        @RequestParam("login") String login, @RequestParam("password") String password) {
        Admin admin = new Admin();
        try {
            admin = adminDAO.getAdmin(login, password);
        } catch (Exception e){

        }
        if (admin.getLogin() != null) {
            return "true";
        }
        return "false";
    }
    // Отправка заявки
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addApplication")
    public void addApplication(HttpServletRequest request,
                               @RequestParam(value = "header", required = false) String header,
                               @RequestPart("photo") MultipartFile photo,
                               @RequestParam("description") String description,
                               @RequestParam("area") int area,
                               @RequestParam("cadastral") String cadastral) throws IOException {
        Application application = new Application();
        application.setIsAccepted("false");
        application.setHeader(header);
        byte[] imageData = photo.getBytes();
        application.setPhoto(imageData);
        application.setDescription(description);
        application.setArea(area);
        application.setCadastral(cadastral);
        applicationDAO.saveApplication(application);
    }

    @GetMapping("/getAllApplications")
    public List<Application> getAllApplications(){
            return applicationDAO.getApplications();
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
