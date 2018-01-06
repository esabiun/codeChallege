package lt.rci.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("develop")
public class TestApplicationTests {

    /**
     * simply test that we have good config, which loads
     */
    @Test
    public void contextLoads() {
    }

}
