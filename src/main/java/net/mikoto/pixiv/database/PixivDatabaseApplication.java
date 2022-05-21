package net.mikoto.pixiv.database;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static net.mikoto.pixiv.api.util.FileUtil.createDir;
import static net.mikoto.pixiv.api.util.FileUtil.createFile;
import static net.mikoto.pixiv.api.util.RsaUtil.getKeyPair;
import static net.mikoto.pixiv.database.constant.Constant.*;

/**
 * @author mikoto2464
 */
@SpringBootApplication
@EntityScan("net.mikoto.pixiv.api.model")
public class PixivDatabaseApplication {
    private static final String AUTO = "auto";

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        createDir("config");
        createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivDatabaseApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
        MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

        // Init key pair
        if (AUTO.equals(MAIN_PROPERTIES.getProperty(RSA_PUBLIC_KEY)) || AUTO.equals(MAIN_PROPERTIES.getProperty(RSA_PRIVATE_KEY))) {
            KeyPair keyPair = getKeyPair();
            MAIN_PROPERTIES.setProperty(RSA_PUBLIC_KEY, new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
            MAIN_PROPERTIES.setProperty(RSA_PRIVATE_KEY, new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
            System.out.println("Using temp keyPair:");
            System.out.println(RSA_PRIVATE_KEY + ":" + MAIN_PROPERTIES.getProperty(RSA_PRIVATE_KEY));
            System.out.println(RSA_PUBLIC_KEY + ":" + MAIN_PROPERTIES.getProperty(RSA_PUBLIC_KEY));
        }

        SpringApplication.run(PixivDatabaseApplication.class, args);
    }
}
