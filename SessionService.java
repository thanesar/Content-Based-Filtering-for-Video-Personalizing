import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class SessionService {

    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    // Create a new session
    public String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, username);
        return sessionId;
    }

    // Get username by session ID
    public String getUsernameBySessionId(String sessionId) {
        return sessions.get(sessionId);
    }

    // Invalidate a session
    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
