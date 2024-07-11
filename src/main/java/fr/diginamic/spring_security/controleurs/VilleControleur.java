package fr.diginamic.spring_security.controleurs;

import fr.diginamic.spring_security.dto.VilleDTO;
import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;
import fr.diginamic.spring_security.repositories.VilleRepository;
import fr.diginamic.spring_security.services.DepartementService;
import fr.diginamic.spring_security.services.VilleService;
import fr.diginamic.spring_security.utilitaire.AnomalieException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    /**
     * villeService
     */
    @Autowired
    public VilleService villeService;

    /**
     * departementService
     */
    @Autowired
    public DepartementService departementService;

    /**
     * villeRepository
     */
    @Autowired
    private VilleRepository villeRepository;

    /**
     * Méthode qui retourne la liste des villes
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Affiche la liste des villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des villes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleDTO.class)) })})
    @GetMapping
    public ResponseEntity<Iterable<VilleTp6>> extraireVilles() {
        return ResponseEntity.ok(villeService.extractVilles());
    }

    @GetMapping("/csvMinHab/{min}")
    public void ficheCsv(@PathVariable int min, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"fichier.csv\"");
        List<VilleTp6> villes = villeRepository.findByNbHabitantsGreaterThan(min);
        PrintWriter writer = response.getWriter();
        for (VilleTp6 ville : villes) {
            String ligne = ville.getNom() + ";" + ville.getNbHabitants() + ";" + ville.getDepartement().getCodeDep() + ";" + ville.getDepartement().getNom();
            writer.println(ligne);
            }
            writer.close();

        response.flushBuffer();

    }


    /**
     * Affiche les villes sous forme de pagination
     * @param page nombre de la page
     * @param size taille d'élément dans la page
     * @return
     */
    @GetMapping("/pagination")
    public Page<VilleDTO> extraireVillesPage(@RequestParam int page, @RequestParam int size) {
        return villeRepository.findAll(PageRequest.of(page, size)).map(villeService::convertirVilleDto);
    }

    /**
     * Affiche les villes selon l'id passé en paramètre
     * requete : villes/rechercheParId?id=1
     * @param id correspond à l'identifiant de la ville
     * @return ResponseEntity<VilleTp6>
     */
    @GetMapping("/rechercheParId")
    public ResponseEntity<VilleTp6> extraireVilleParId(@RequestParam int id) {
        return ResponseEntity.ok(villeService.findById(id));
    }

    /**
     * Affiche les villes selon le nom reçu en paramètre
     * requete : villes/rechercheParNom/Montpellier
     * @param nom correspond au nom de la ville
     * @return ResponseEntity<VilleTp6>
     */
    @GetMapping("/rechercheParNom/{nom}")
    public ResponseEntity<VilleTp6> extraireVilleParNom(@PathVariable String nom) {
        return ResponseEntity.ok(villeService.findByNom(nom));
    }

    /**
     * Affiche les villes dont le nom commence par la chaine de caractère passée en paramètre
     * requete : villes/rechercheNomCommence/Par
     * @param nom correspond au nom de la ville
     * @return ResponseEntity<String>
     */
    @GetMapping("/rechercheNomCommence/{nom}")
    public ResponseEntity<String> extraireVilleCommencePar(@PathVariable String nom) throws AnomalieException {
        List<VilleTp6> villeListe = villeService.findByNomStartingWith(nom);
        if (villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville dont le nom commence par " + nom + " n'a été trouvée");
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Affiche une liste des villes dont la population minimal correspond au nombre reçu en paramètre
     * requete : villes/rechercheVillePopMin/150000
     *
     * @param nb correspond au nombre minimal d'habitant
     * @return ResponseEntity<String>
     */
    @GetMapping("/rechercheVillePopMin/{nb}")
    public ResponseEntity<String> extraireVillePopMin(@PathVariable int nb) throws AnomalieException {
        List<VilleTp6> villeListe = villeService.findByNbHabitantsGreaterThan(nb);
        if (!villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville n'a une population supérieur à " + nb);
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Affiche la liste des villes dont la population est comprise entre les deux nombres reçu en paramètre
     * requete : villes/recherchePopEntre?min=1&max=2500
     *
     * @param min nombre minimal d'habitant
     * @param max nombre maximal d'habitant
     * @return ResponseEntity<String>
     */
    @GetMapping("/recherchePopEntre")
    public ResponseEntity<String> extraireVillePopMinAndMax(@RequestParam int min, @RequestParam int max) throws AnomalieException {
        List<VilleTp6> villeListe = villeService.findByNbHabitantsBetween(min, max);
        if (villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville n'a une population comprise entre " + min + " et " + max);
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Affiche la liste des villes d'un département avec une population minimale reçu en paramètre
     * requete : villes/recherchePopDepVille?idDep=1&min=15000
     *
     * @param idDep numéro de l'identifiant du département
     * @param min   nombre minimal d'habitant
     * @return ResponseEntity<String>
     */
    @GetMapping("/recherchePopDepVille")
    public ResponseEntity<String> extraireVillePopMinInDepartement(@RequestParam int idDep, @RequestParam int min) throws AnomalieException {
        DepartementTp6 departement = departementService.findById(idDep);
        List<VilleTp6> villeListe = villeService.findByDepartementAndNbHabitantsGreaterThan(departement, min);
        if(villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville n'a une population supérieure à " + min + " dans le département " + idDep);
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Affiche la liste des villes d'un département reçu en paramètre ayant une population comprise entre deux valeurs
     * requete : villes/recherchePopDepVilleEntre?idDep=2&min=15000&max=20000
     *
     * @param idDep numéro d'identifiant du département
     * @param min   nombre minimal d'habitant
     * @param max   nombre maximal d'habitant
     * @return ResponseEntity<String>
     */
    @GetMapping("/recherchePopDepVilleEntre")
    public ResponseEntity<String> extraireVillePopMinInDepartement(@RequestParam int idDep, @RequestParam int min,
                                                                   @RequestParam int max) throws AnomalieException {
        DepartementTp6 departement = departementService.findById(idDep);
        List<VilleTp6> villeListe = villeService.findByDepartementAndNbHabitantsBetween(departement, min, max);
        if(villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville n'a une population comprise entre " + min + " et " + max + " dans le département " + idDep);
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Affiche le nombre de villes souhaités en fonction d'un département donné
     * requete : villes/rechercheDesNVilleDep?idDep=2&nombre=15000
     * @param idDep  numéro d'identifiant du département
     * @param nombre nombre de villes à afficher
     * @return ResponseEntity<String>
     */
    @GetMapping("/rechercheDesNVilleDep")
    public ResponseEntity<String> extraireNVilleInDepartement(@RequestParam int idDep, @RequestParam int nombre) throws AnomalieException {
        DepartementTp6 departement = departementService.findById(idDep);
        List<VilleTp6> villeListe = villeService.TopNVillesByDepartementMaxPop(departement, nombre);
        if(villeListe.isEmpty()) {
            throw new AnomalieException("Aucune ville n'est trouvé avec une population minimale de " + nombre + " dans le département " + idDep);
        }
        return ResponseEntity.ok(villeListe.toString());
    }

    /**
     * Insère une ville en base de donnée
     * @param nvVille Correspond aux données d'une nouvelle ville
     * @param result  permet de tester les validations
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Création d'une nouvelle ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des villes incluant la dernière ville créée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleTp6.class)) }),
            @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> insererVille(@Valid @RequestBody VilleTp6 nvVille, BindingResult result) throws AnomalieException {
        if (result.hasErrors()) {
            throw new AnomalieException(result.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining("\n")));
        }
        villeService.insertVille(nvVille);
        return ResponseEntity.ok(villeService.extractVilles().toString());
    }

    /**
     * Permet de modifier une ville située en base de donnée
     * @param id        correspond à l'identifiant de la ville
     * @param editVille correspond aux données de la ville
     * @param result    vérifie les données entrées
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Modification d'une nouvelle ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des villes incluant la dernière ville modifiée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleTp6.class)) }),
            @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@Valid @PathVariable int id, @RequestBody VilleTp6 editVille, BindingResult result) throws AnomalieException {
        if (result.hasErrors()) {
            throw new AnomalieException(result.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining("\n")));
        }
        VilleTp6 ville = villeService.findById(id);
        if (ville != null) {
            villeService.modifierVille(editVille, id);
            return ResponseEntity.ok(villeService.extractVilles().toString());
        }
        return ResponseEntity.badRequest().body("La ville n'existe pas");
    }

    /**
     * Permet de supprimer une ville de la base de donnée
     * @param id identifiant de la ville
     * @return ResponseEntity<String>
     */
    @Operation(summary = "Supprime une ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retourne la liste des villes",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleTp6.class)) }),
            @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws AnomalieException {
        VilleTp6 ville = villeService.findById(id);
        if (ville == null) {
            throw new AnomalieException("Ville n'existe pas");
        }
        villeService.supprimerVille(id);
        return ResponseEntity.ok(villeService.extractVilles().toString());
    }
}
