package session.management.version2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

// @Setter 사용 지양

@Getter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickName;

	// 기본 생성자
	public Users() {

	}

	public Users(String nickname) {
		this.nickName = nickname;
	}

	public void namingNickname(String nickName) {
		this.nickName = nickName;
	}
}
