package session.management.version1.repository;

import session.management.version1.session.GameSession;

import java.util.Optional;

public interface GameSessionRepository {
	void save(GameSession session);						// 세션 생성
	Optional<GameSession> findById(String sessionId);	// 세션 조회
	void deleteById(String sessionId);					// 세션 삭제
}
