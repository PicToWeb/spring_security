package fr.diginamic.spring_security.controleurs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControleur {

    @GetMapping("/home")
    public String getHome() {
        return "index";
    }
}
