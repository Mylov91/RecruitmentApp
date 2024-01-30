package ru.mylov.Recruitment.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity class describing the users table from the database and implementing the UserDetails interface necessary
 * for the correct work of the authorization functionality
 * @author Mylov Sergey <mylov91@yandex.ru>
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /** Empty constructor necessary for Spring Boot */
    public User() {
    }

    /** id field in database */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /** username field in database (should contain email address for correct html form verification) */
    @Column(name = "username")
    String username;

    /** password field in database */
    @Column(name = "password")
    String password;

    /** role field in database
     * @see Role
     * */
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    /** firstname field in database */
    @Column(name = "firstname")
    String firstname;

    /** lastname field in database */
    @Column(name = "lastname")
    String lastname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    /**
     * Override standard getAuthorities method from UserDetails interface
     * @return list with all roles of this user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
        authorityList.add(authority);
        return authorityList;
    }

    /**
     * Override standard isAccountNonExpired method from UserDetails interface
     * @return true because of account lifetime functionality is not realized
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Override standard isAccountNonLocked method from UserDetails interface
     * @return true because of account lock functionality is not realized
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Override standard isCredentialsNonExpired method from UserDetails interface
     * @return true because of credintials lifetime functionality is not realized
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Override standard isEnabled method from UserDetails interface
     * @return true because of disable account functionality is not realized
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
