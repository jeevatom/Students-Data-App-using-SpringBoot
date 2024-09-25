package com.java.students_base.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java.students_base.model.UserSession;
import java.util.*;
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
     UserSession findBySessionId(String sessionId);

    List<UserSession> findByUsername(String username);
}
