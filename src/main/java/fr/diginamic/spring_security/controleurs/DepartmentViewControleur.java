package fr.diginamic.spring_security.controleurs;

import fr.diginamic.spring_security.dto.DepartementDTO;
import fr.diginamic.spring_security.dto.VilleDTO;
import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;
import fr.diginamic.spring_security.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class DepartmentViewControleur {

    @Autowired
    private DepartementService departementService;


    @GetMapping("/departementList")
    public String getDpartement(Model model) {
        Iterable<DepartementTp6> allTown = departementService.extractDepartement();
        List<DepartementDTO> townList = StreamSupport.stream(allTown.spliterator(), false).map(v -> departementService.convertirDepartementDto(v)).collect(Collectors.toList());
        model.addAttribute("departements", townList);
        return "departement/departementList";
    }
    @GetMapping("/deleteDepartement/{codeDep}")
    public String deleteDepartement(@PathVariable String codeDep) {
        DepartementTp6 dep = departementService.extractDepCode(codeDep);
        departementService.supprimerDepartement(dep.getId());
        return "redirect:/departementList";
    }
}
