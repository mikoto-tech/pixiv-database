package net.mikoto.pixiv.database;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class PixivDatabaseApplicationTests {

    @Test
    void DataDaoTest() throws SQLException, InterruptedException {
        Thread.sleep(5);
    }
}
