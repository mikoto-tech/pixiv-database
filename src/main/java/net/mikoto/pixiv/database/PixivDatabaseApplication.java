package net.mikoto.pixiv.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author mikoto2464
 */
@SpringBootApplication
@EntityScan("net.mikoto.pixiv.core.model")
public class PixivDatabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(PixivDatabaseApplication.class, args);
    }
}
