package net.mikoto.pixiv.database;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author mikoto2464
 */
@SpringBootApplication
@EntityScan(basePackages = {
        "net.mikoto.pixiv.core.model",
        "net.mikoto.pixiv.database.model"
})
@EnableJpaRepositories(basePackages = "net.mikoto.pixiv.database.dao")
@ComponentScan("net.mikoto.pixiv.core.client")
@ComponentScan("net.mikoto.pixiv.database")
@ForestScan("net.mikoto.pixiv.core.client")
public class PixivDatabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(PixivDatabaseApplication.class, args);
    }
}
