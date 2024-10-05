package session.management.version1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import session.management.version1.session.GameSession;
import session.management.version1.repository.GameSessionRepository;
import session.management.version1.exception.NotFoundSessionException;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class GameController {

	private final GameSessionRepository gameSessionRepository;

	@Autowired
	public GameController(GameSessionRepository gameSessionRepository) {
		this.gameSessionRepository = gameSessionRepository;
	}

	// 게임 시작 시 세션을 생성
	@PostMapping("/start")
	public ResponseEntity<String> startGame(HttpServletResponse response) {
		log.info("세션 생성");
		String sessionId = UUID.randomUUID().toString();	// 01d34f47-ad87-4294-bffd-68a1017a2829
		GameSession gameSession = new GameSession(sessionId);
		gameSessionRepository.save(gameSession);

		Cookie cookie = new Cookie("GAME_SESSION", sessionId);
		cookie.setMaxAge(3600);	// 세션 생명주기 : 1시간
		cookie.setPath("/");
		response.addCookie(cookie);
		return ResponseEntity.ok(sessionId);
	}

	// 세션이 잘 관리되는지 확인하기
	@GetMapping("/check")
	public ResponseEntity<GameSession> check(HttpServletResponse response, @CookieValue("GAME_SESSION") String sessionId) throws IOException {
		log.info("세션 체크");
		GameSession gameSession = gameSessionRepository.findById(sessionId).orElseThrow(
				() -> new NotFoundSessionException("세션을 찾을 수 없습니다.")	// Http Status 400
		);
		return ResponseEntity.ok(gameSession);
	}

	// {
	//    "sessionId": "01d34f47-ad87-4294-bffd-68a1017a2829"
	// }

	// {
	//    "timestamp": "2024-10-05T15:51:45.000+00:00",
	//    "status": 400,
	//    "error": "Bad Request",
	//    "path": "/check"
	// }
	
	// 게임 종료 시 세션 삭제
	@PostMapping("/end")
	public ResponseEntity<String> endGame(HttpServletResponse response, @CookieValue("GAME_SESSION") String sessionId) {
		log.info("세션 삭제");
		gameSessionRepository.deleteById(sessionId);

		Cookie cookie = new Cookie("GAME_SESSION", null);
		cookie.setMaxAge(0);	// 세션 생명주기 : 0
		cookie.setPath("/");
		response.addCookie(cookie);
		return ResponseEntity.ok("Game End!");
	}

	// Game End!
}
