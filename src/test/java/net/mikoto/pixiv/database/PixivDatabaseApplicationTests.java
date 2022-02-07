package net.mikoto.pixiv.database;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.DATA_DAO;

@SpringBootTest
class PixivDatabaseApplicationTests {

    @Test
    void DataDaoTest() throws SQLException, InterruptedException {
        Thread.sleep(5);
        System.out.println(
                DATA_DAO.queryData("select count(*) from bookmark_count_index_1;")
                        .getInt("count(*)"));
    }
}
