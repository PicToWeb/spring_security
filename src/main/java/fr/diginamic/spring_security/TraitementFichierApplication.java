package fr.diginamic.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.diginamic.spring_security.services.VilleService;
import fr.diginamic.spring_security.utilitaire.RecensementUtils;

@SpringBootApplication
public class TraitementFichierApplication implements CommandLineRunner{

	@Autowired
	private VilleService villeService;
	
	@Value("${initialisation.base}")
	private boolean initialisationBase;
	
	public static void main(String[] args) {	
		SpringApplication application = new SpringApplication(TraitementFichierApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);		
	}
	
	@Override
	public void run(String... args) throws Exception{
		
		if(!initialisationBase) {
			System.out.println("la base est déjà initialisée.");
			return;
		}
		String filePath = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();
		villeService.addDataToBase(RecensementUtils.lire(filePath));	
	}

}
