package com.erich.factura.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "logout",required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash) {
        if(principal != null){
            flash.addFlashAttribute("info","Ya tiene session");
            return "redirect:/listar";
        }
        if(error != null){
            model.addAttribute("error","Login error incorrect username or password");
        }
        if(logout != null){
            model.addAttribute("success","Ha Cerrado session con exito!!");
        }
        return "login";
    }
}
