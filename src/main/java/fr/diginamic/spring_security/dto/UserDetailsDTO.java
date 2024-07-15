package fr.diginamic.spring_security.dto;

import fr.diginamic.spring_security.entity.UserAccount;
import fr.diginamic.spring_security.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsDTO implements UserDetailsService {

//
//    @Autowired
//    private UserAccountRepository userAccountRepository;
//
//    @Override
//    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAccount userAccount = userAccountRepository.findByUsername(username);
//        if (userAccount == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return User.withDefaultPasswordEncoder()
//                .username(userAccount.getUsername())
//                .password(userAccount.getPassword())
//                .authorities(userAccount.getAuthorities()).build();
//
//    }
//}
