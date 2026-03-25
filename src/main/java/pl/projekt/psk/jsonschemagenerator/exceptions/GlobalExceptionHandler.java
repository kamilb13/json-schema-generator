package pl.projekt.psk.jsonschemagenerator.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("jsonInput", request.getAttribute("jsonInput"));
        model.addAttribute("isValid", false);
        model.addAttribute("error", ex.getMessage());
        return "index";
    }
}