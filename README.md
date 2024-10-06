# 일회성 게임의 세션 관리를 위한 방법들 정리해보기

---

* HttpSession with Redis
  * Java-based Configuration
    * Redis Java-based Configuration


* Spring Java Configuration
  * `@EnableRedisHttpSession` : Filter 인터페이스를 구현한 springSessionRepositoryFilter라는 이름의 스프링 빈을 생성
  * Spring Session을 Redis Server에 연결하는 RedisConnectionFactory를 생성해야 한다.
  * 기본 포트는 6379번

```java
@Configuration(proxyBeanMethods = false)
@EnableRedisHttpSession 
public class Config {

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(); 
	}

}
```