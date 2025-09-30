package com.yayfan.music;

import com.yayfan.music.integration.conversion.YtDlpAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest
class MusicApplicationTests {

	@MockBean
	private YtDlpAdapter ytDlpAdapter;

	@Test
	void contextLoads() {
	}

}
