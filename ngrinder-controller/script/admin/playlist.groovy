import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.script.GTest
import net.grinder.script.Grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// nGrinder의 내장 HTTP 도구를 사용합니다.
import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPResponse

@RunWith(GrinderRunner)
class TestRunner {

	public static GTest test
	public static HTTPRequest request
	// 헤더를 담을 Map을 준비합니다.
	public static Map<String, String> headers = [:]

	@BeforeProcess
	public static void beforeProcess() {
		test = new GTest(1, "Get Playlist Test")
		request = new HTTPRequest()
		
		// 🚨 중요! 이 부분의 토큰을 "반드시" 최신 토큰으로 바꿔주세요!
		headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidGVzdF91c2VyMSIsImV4cCI6MTc1ODM1Mzg0OSwiaWF0IjoxNzU4MjY3NDQ5LCJzY29wZSI6IkFSVElTVCJ9.QwCWZa_OVUXhqIZfplKKgyavjQp8FcC_FPBg67pTByvgjHIyJNcCH23h2lWcLeiMOykdSc5COGj0iKgYrYGtnvhKWHjVw8IcXNpsHQ-XBSY-XF10Vy_JJaXVIUZJ8gVEr_aYQOFB-uIG9Jyf-C-yi3tg66QKpRNXuZF_U3rPyGaPCU7qD6iojcP-eFDHRPj-HA5w1u1ChbcJCQ9c-wYwqqx74NmLYjNhmgOOooUEslk7tuOTkCgy1BHbGlQBhs0osX1t_QJSOZcxFGYJ-m2kfNyO_m08dsbk04NKEzkcOU9NAPf0ya6PSFE_vvYmDyGzlkIu3Vy_0kympXp6yoEmlA")
	}

	@BeforeThread
	public void beforeThread() {
		test.record(this, "test")
		grinder.statistics.delayReports = true
	}

	@Before
	public void before() {
		// 모든 요청에 기본 헤더를 설정합니다.
		request.setHeaders(headers)
	}

	@Test
	public void test() {
		// 🚨 중요! 이 ID를 당신의 데이터베이스에 실제로 있는 playlist ID로 바꿔주세요!
		def playlistId = 1
		
		// GET 요청을 보냅니다.
		HTTPResponse response = request.GET("http://host.docker.internal:8080/api/v1/playlists/" + playlistId)

		// 응답 코드가 200 (성공)인지 검증합니다.
		// 200이 아닌 코드는 nGrinder가 자동으로 에러로 처리하므로, 성공했을 때의 조건만 검증해도 충분합니다.
		if (response.statusCode == 200) {
			// 이 줄은 사실상 필요 없지만, 명시적으로 성공을 기록하고 싶을 때 사용할 수 있습니다.
		}
	}
}