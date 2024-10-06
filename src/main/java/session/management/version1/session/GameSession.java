package session.management.version1.session;

public class GameSession {

	private String sessionId;	// 세션은 불변으로 설계
	private int score;

	public GameSession(String sessionId) {
		this.sessionId = sessionId;
		this.score = 0;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void incrementScore() {
		score++;
	}
}
