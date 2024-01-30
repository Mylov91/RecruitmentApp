package ru.mylov.Recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mylov.Recruitment.repository.UserRepository;

/**
 *
 Service class for loading a user from the database. Implements UserDetailsService because it is necessary
 to override the standard loadUserByUsername method for the Spring Security authorization functionality
 * @author Mylov Sergey <mylov91@yandex.ru>
 * @version 1.0
 */
@Service
public class UserService implements UserDetailsService {

    /**
     * Inject repository for user searching
     */
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method for getting user credentials
     * @param username contains the user's email by which we search for him in the database
     * @return List with user credentials mapped to UserDetails list view
     * @throws UsernameNotFoundException if user not found in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
