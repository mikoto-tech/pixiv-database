package net.mikoto.pixiv.database;

import net.mikoto.pixiv.database.dao.impl.ArtworkDao;
import net.mikoto.pixiv.database.dao.impl.UserDao;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;

import static net.mikoto.pixiv.database.constant.Properties.MAIN_PROPERTIES;
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
    public static ArtworkDao ARTWORK_DAO;
    public static UserDao USER_DAO;

    public static void main(String[] args) {
        try {
            createDir("config");
            createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivDatabaseApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
            MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

            ARTWORK_DAO = new ArtworkDao(MAIN_PROPERTIES.getProperty("URL"), MAIN_PROPERTIES.getProperty("USERNAME"), MAIN_PROPERTIES.getProperty("PASSWORD"));
            USER_DAO = new UserDao(MAIN_PROPERTIES.getProperty("URL"), MAIN_PROPERTIES.getProperty("USERNAME"), MAIN_PROPERTIES.getProperty("PASSWORD"));

            ARTWORK_DAO.initIndexDataMap();

            SpringApplication.run(PixivDatabaseApplication.class, args);
        } catch (IOException | UnknownServiceTypeException | SQLException e) {
            e.printStackTrace();
        }
    }
}
