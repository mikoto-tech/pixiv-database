package net.mikoto.pixiv.database;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static net.mikoto.pixiv.database.constant.Properties.MAIN_PROPERTIES;
import static net.mikoto.pixiv.database.util.FileUtil.createDir;
import static net.mikoto.pixiv.database.util.FileUtil.createFile;

/**
 * @author mikoto2464
 */
@SpringBootApplication
@EntityScan("net.mikoto.pixiv.api.pojo")
public class PixivDatabaseApplication {

    public static void main(String[] args) {
        try {
            createDir("config");
            createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivDatabaseApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
            MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

            SpringApplication.run(PixivDatabaseApplication.class, args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
