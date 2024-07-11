package fr.diginamic.spring_security.utilitaire;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

public abstract class Validator {
	
	public static ResponseEntity<String> verif(@Valid BindingResult result){
		if(result.hasErrors()){
			return ResponseEntity.badRequest().body("Les données passées en param sont incorrectes");
		}
		return null;
	}

}
