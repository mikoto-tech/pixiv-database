package net.mikoto.pixiv.database;

import net.mikoto.pixiv.api.pojo.ServiceType;
import net.mikoto.pixiv.database.dao.impl.PixivDataDao;
import net.mikoto.pixiv.database.dao.impl.UserDao;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.service.TokenService;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static net.mikoto.pixiv.database.util.FileUtil.createDir;
import static net.mikoto.pixiv.database.util.FileUtil.createFile;

/**
 * @author mikoto2464
 */
@SpringBootApplication
public class PixivDatabaseApplication {
    /**
     * Constants
     */
    public static final Properties PROPERTIES = new Properties();
    public static PixivDataDao PIXIV_DATA_DAO;
    public static UserDao USER_DAO;

    public static void main(String[] args) {
        try {
            createDir("config");
            createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivDatabaseApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
            PROPERTIES.load(new FileReader("config/config.properties"));

            PIXIV_DATA_DAO = new PixivDataDao(PROPERTIES.getProperty("URL"), PROPERTIES.getProperty("USERNAME"), PROPERTIES.getProperty("PASSWORD"));
            USER_DAO = new UserDao(PROPERTIES.getProperty("URL"), PROPERTIES.getProperty("USERNAME"), PROPERTIES.getProperty("PASSWORD"));

            PIXIV_DATA_DAO.initIndexDataMap();

            TokenService.getInstance().addToken(ServiceType.PATCHER, "PixivPatcherTestToken");

            SpringApplication.run(PixivDatabaseApplication.class, args);
        } catch (IOException | UnknownServiceTypeException | SQLException e) {
            e.printStackTrace();
        }
    }
}
