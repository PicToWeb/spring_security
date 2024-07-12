package fr.diginamic.spring_security.controleurs;

import fr.diginamic.spring_security.dto.VilleDTO;
import fr.diginamic.spring_security.entity.VilleTp6;
import fr.diginamic.spring_security.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class TownViewControleur {

    @Autowired
    private VilleService villeService;


    @GetMapping("/townList")
    public String getVille(Model model) {
        Iterable<VilleTp6> allTown = villeService.extractVilles();
        List<VilleDTO> townList = StreamSupport.stream(allTown.spliterator(), false).map(v -> villeService.convertirVilleDto(v)).collect(Collectors.toList());
        model.addAttribute("towns", townList);
        return "town/townList";
    }
    @GetMapping("/deleteTown/{id}")
    public String deleteTown(@PathVariable int id) {
        villeService.supprimerVille(id);
        return "redirect:/townList";
    }
}
