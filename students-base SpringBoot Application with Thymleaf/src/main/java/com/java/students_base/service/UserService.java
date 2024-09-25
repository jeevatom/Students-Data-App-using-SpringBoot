package com.java.students_base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.java.students_base.UserRepository.UserRepository;
import com.java.students_base.UserRepository.UserSessionRepository;
import com.java.students_base.model.User;
import com.java.students_base.model.UserSession;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole())));
    }

   public String registerUser(User user) {
        try {
          
            user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
            user.setRole("ROLE_USER");

          
            userRepository.save(user);
            return "SUCCESS";

        } catch (DataIntegrityViolationException e) {
            
            return "USERNAME_EXISTS";
        } catch (Exception e) {
            return "ERROR";
        }
    }


public void saveUserSession(String sessionId) {
        UserSession userSession = new UserSession();
        userSession.setSessionId(sessionId);
        userSession.setLoginTime(LocalDateTime.now());
        userSessionRepository.save(userSession);
    }

    public void updateLogoutTime(String sessionId) {
        UserSession userSession = userSessionRepository.findBySessionId(sessionId);
        if (userSession != null) {
            userSession.setLogoutTime(LocalDateTime.now());
            userSessionRepository.save(userSession);
        }
    }

}
