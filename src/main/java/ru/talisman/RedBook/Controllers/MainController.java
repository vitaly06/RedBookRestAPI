package ru.talisman.RedBook.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.talisman.RedBook.DAO.AdminDAO;
import ru.talisman.RedBook.Models.Admin;
import ru.talisman.RedBook.Utill.AdminErrorResponse;
import ru.talisman.RedBook.Utill.AdminNotFoundException;

@RestController
@CrossOrigin
public class MainController {
    @Autowired
    AdminDAO adminDAO;

    @PostMapping("/loginAdmin")
    public Admin loginAdmin(HttpServletRequest request,
        @RequestParam("login") String login, @RequestParam("password") String password) {
        Admin admin = adminDAO.getAdmin(login, password);
        return admin;
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
