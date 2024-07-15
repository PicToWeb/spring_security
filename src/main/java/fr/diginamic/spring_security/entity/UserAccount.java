package fr.diginamic.spring_security.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities;

    /**
     *
     */
    public UserAccount() {
    }

    public UserAccount(String username, String password,String role) {
        this.username = username;
        this.password = password;
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(role);
        this.authorities = new ArrayList<>();
        this.authorities.add(roleAuthority);
    }

//    public UserAccount(String username, String password,String ... authorities) {
//        this.username = username;
//        this.password = password;
//        this.authorities = Arrays.stream(authorities)
//                .map(SimpleGrantedAuthority::new)
//                .map(GrantedAuthority.class::cast)
//                .toList();
//    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param authorities
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     *
     * @return
     */
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
