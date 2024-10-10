package session.management.version2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import session.management.version2.domain.Users;
import session.management.version2.exception.NotFoundSessionExceptionV2;
import session.management.version2.repository.GameSessionRepositoryV2;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameControllerV2 {

	private final GameSessionRepositoryV2 gameSessionRepository;

	@PostMapping("/v2/start")
	public ResponseEntity<String> startGame(HttpServletRequest request, @RequestBody String nickname) {
		log.info("세션 생성");
		HttpSession session = request.getSession();
		Users users = new Users(nickname);

		Users savedUser = gameSessionRepository.save(users);
		session.setAttribute("userId", savedUser.getId());
		session.setAttribute("nickname", savedUser.getNickName());
		return ResponseEntity.ok("사용자가 게임에 접속했습니다!");
	}
	
  	@GetMapping("/v2/check")
	public ResponseEntity<Users> check(HttpServletRequest request) {
		log.info("세션 조회");
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			throw new NotFoundSessionExceptionV2("세션을 찾을 수 없습니다.");
		}

		Long userId = (Long) session.getAttribute("userId");
		
		// 세션 정보 조회
		log.info("sessionId={}", session.getId());
		log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
		log.info("creationTime={}", new Date(session.getCreationTime()));
		log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
		log.info("isNew={}", session.isNew());
		log.info("userId={}", session.getAttribute("userId"));
		log.info("nickname={}", session.getAttribute("nickname"));
		
		Users users = gameSessionRepository.findById(userId).orElseThrow(() -> new NotFoundSessionExceptionV2("세션을 찾을 수 없습니다."));
		return ResponseEntity.ok(users);
	}

	@PostMapping("/v2/end")
	public ResponseEntity<String> endGame(HttpServletRequest request) {
		log.info("게임 종료");
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			throw new NotFoundSessionExceptionV2("Session not found");
		}
		Long userId = (Long) session.getAttribute("userId");
		gameSessionRepository.deleteById(userId);
		session.invalidate();
		return ResponseEntity.ok("게임이 종료되었고 세션을 무효화합니다.");
	}
}
