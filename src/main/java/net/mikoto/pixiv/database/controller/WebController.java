package net.mikoto.pixiv.database.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static net.mikoto.pixiv.database.constant.Constant.MAIN_PROPERTIES;
import static net.mikoto.pixiv.database.constant.Constant.RSA_PUBLIC_KEY;

/**
 * @author mikoto
 * @date 2022/5/7 20:42
 */
@RestController
@RequestMapping(
        "/web"
)
public class WebController {
    @RequestMapping(
            value = "/publicKey",
            method = RequestMethod.GET
    )
    public String publicKeyHttpApi() {
        return MAIN_PROPERTIES.getProperty(RSA_PUBLIC_KEY);
    }
}
