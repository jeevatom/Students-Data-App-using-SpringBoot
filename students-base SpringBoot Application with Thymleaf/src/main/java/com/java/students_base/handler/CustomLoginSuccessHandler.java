package com.java.students_base.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.java.students_base.UserRepository.UserSessionRepository;
import com.java.students_base.model.UserSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        LocalDateTime loginTime = LocalDateTime.now();
    
        UserSession userSession = new UserSession();
        userSession.setUsername(username);
        userSession.setSessionId(sessionId);
        userSession.setLoginTime(loginTime);
    
        try {
            userSessionRepository.save(userSession);
            System.out.println("User session saved: " + userSession); // Log the session details
        } catch (Exception e) {
            System.err.println("Error saving user session: " + e.getMessage());
        }
    
        response.sendRedirect("http://localhost:8080/students/all_students");
    }
    
    

    
}
