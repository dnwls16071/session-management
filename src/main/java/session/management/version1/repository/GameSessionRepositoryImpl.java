package session.management.version1.repository;

import org.springframework.stereotype.Repository;
import session.management.version1.session.GameSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameSessionRepositoryImpl implements GameSessionRepository{

	// key : sessionId, value : GameSession
	private Map<String, GameSession> memoryMap = new HashMap<>();

	@Override
	public void save(GameSession session) {
		memoryMap.put(session.getSessionId(), session);
	}

	@Override
	public Optional<GameSession> findById(String sessionId) {
		return Optional.ofNullable(memoryMap.get(sessionId));
	}

	@Override
	public void deleteById(String sessionId) {
		memoryMap.remove(sessionId);
	}
}
