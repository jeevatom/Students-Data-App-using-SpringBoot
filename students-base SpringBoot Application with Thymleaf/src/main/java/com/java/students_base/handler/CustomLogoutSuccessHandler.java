package com.java.students_base.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.java.students_base.UserRepository.UserSessionRepository;
import com.java.students_base.model.UserSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            String username = authentication.getName();
            LocalDateTime logoutTime = LocalDateTime.now();

            // Find all user sessions by username
            List<UserSession> userSessions = userSessionRepository.findByUsername(username);
            if (!userSessions.isEmpty()) {
                for (UserSession userSession : userSessions) {
                    userSession.setLogoutTime(logoutTime);
                    userSessionRepository.save(userSession);
                    System.out.println("User session updated with logout time: " + userSession);
                }
            } else {
                System.err.println("User session not found for username: " + username);
            }
        }
        response.sendRedirect("http://localhost:8080/"); // Redirect after logout
    }
}
