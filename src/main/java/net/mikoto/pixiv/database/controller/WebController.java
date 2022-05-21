package net.mikoto.pixiv.database.controller;

import net.mikoto.pixiv.api.http.database.web.PublicKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_WEB;
import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_WEB_PUBLIC_KEY;
import static net.mikoto.pixiv.database.constant.Constant.MAIN_PROPERTIES;
import static net.mikoto.pixiv.database.constant.Constant.RSA_PUBLIC_KEY;

/**
 * @author mikoto
 * @date 2022/5/7 20:42
 */
@RestController
@RequestMapping(
        DATABASE_WEB
)
public class WebController implements PublicKey {
    @RequestMapping(
            value = DATABASE_WEB_PUBLIC_KEY,
            method = RequestMethod.GET
    )
    @Override
    public String publicKeyHttpApi() {
        return MAIN_PROPERTIES.getProperty(RSA_PUBLIC_KEY);
    }
}
