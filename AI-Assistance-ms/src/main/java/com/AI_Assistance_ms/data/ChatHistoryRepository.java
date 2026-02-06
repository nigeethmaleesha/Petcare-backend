package com.AI_Assistance_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findBySessionIdOrderByTimestampAsc(String sessionId);
    List<ChatHistory> findByIsEmergencyTrueOrderByTimestampDesc();
}