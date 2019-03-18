import main.java.log.LogLevel;
import main.java.log.LogWindowSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LogWindowTests {

    @Test
    void addZeroMessages() {
        var logger = new LogWindowSource(10);

        assertEquals(logger.size(), 0);
    }

    @Test
    void addLessThanMaximumMessages() {
        var length = 10;
        var logger = new LogWindowSource(length);

        for (var i = 0; i < length/2; i++) {
            logger.append(LogLevel.Info, "msg");
        }

        assertEquals(logger.size(), length/2);
    }

    @Test
    void addMoreThanMaximumMessages() {
        var length = 10;
        var logger = new LogWindowSource(length);

        for (var i=0; i < length*2; i++) {
            logger.append(LogLevel.Info, "msg");
        }

        assertEquals(logger.size(), length);
    }
}
