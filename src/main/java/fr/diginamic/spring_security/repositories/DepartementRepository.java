package fr.diginamic.spring_security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.diginamic.spring_security.entity.DepartementTp6;


@Repository
public interface DepartementRepository extends CrudRepository<DepartementTp6,Integer> {
	

}
