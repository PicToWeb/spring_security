package fr.diginamic.spring_security.controleurs;

import fr.diginamic.spring_security.services.DepartementService;
import fr.diginamic.spring_security.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TownViewControleurv2 {

    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;


    @GetMapping("/townList2")
    public ModelAndView getVille() {
       Map<String,Object> model = new HashMap<>();
       model.put("towns",villeService.extractVilles());
       model.put("departements",departementService.extractDepartement());
        return new ModelAndView("town/townList2",model);
    }
    @DeleteMapping("/deleteTown2/{id}")
    public String deleteTown(@PathVariable int id) {
        villeService.supprimerVille(id);
        return "redirect:/townList2";
    }
}
