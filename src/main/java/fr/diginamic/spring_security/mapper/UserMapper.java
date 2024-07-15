package fr.diginamic.spring_security.mapper;

import fr.diginamic.spring_security.entity.UserAccount;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * To map useracount to userdetails
 */
public class UserMapper {

    public static UserDetails toUserDetails (UserAccount userAccount){
        return User.builder().username(userAccount.getUsername()).password(userAccount.getPassword()).authorities(userAccount.getAuthorities()).build();
    }
}
