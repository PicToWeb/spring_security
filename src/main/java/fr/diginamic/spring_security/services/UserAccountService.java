package fr.diginamic.spring_security.services;


import fr.diginamic.spring_security.entity.UserAccount;
import fr.diginamic.spring_security.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        create(new UserAccount("admin", passwordEncoder.encode("admin"),"ROLE_ADMIN"));
        create(new UserAccount("user", passwordEncoder.encode("user"),"ROLE_USER"));
    }


    public void create(UserAccount user) {
        userAccountRepository.save(user);
    }

    public void update(UserAccount user) {
        userAccountRepository.save(user);
    }

    public void delete(UserAccount user) {
        userAccountRepository.delete(user);
    }

}
