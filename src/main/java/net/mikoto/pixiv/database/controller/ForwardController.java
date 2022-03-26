package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.http.database.forward.Login;
import net.mikoto.pixiv.api.http.forward.web.PublicKey;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_FORWARD;
import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_FORWARD_LOGIN;
import static net.mikoto.pixiv.api.util.HttpApiUtil.getHttpApi;
import static net.mikoto.pixiv.database.constant.ForwardService.FORWARD_SERVICE;
import static net.mikoto.pixiv.database.constant.Properties.MAIN_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/3/19 15:26
 */
@RestController
public class ForwardController implements Login {
    private static final String ACCESS_KEY = "ACCESS_KEY";
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    @RequestMapping(
            value = DATABASE_FORWARD + DATABASE_FORWARD_LOGIN,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject loginHttpApi(@NotNull HttpServletResponse response,
                                   @RequestParam @NotNull String accessKey,
                                   @RequestParam String address) throws NoSuchMethodException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        JSONObject outputJsonObject = new JSONObject();

        if (accessKey.equals(MAIN_PROPERTIES.getProperty(ACCESS_KEY))) {
            Request loginRequest = new Request.Builder()
                    .url(address + getHttpApi(PublicKey.class))
                    .get()
                    .build();
            Response loginResponse = OK_HTTP_CLIENT.newCall(loginRequest).execute();
            outputJsonObject.put("success", true);
            FORWARD_SERVICE.insertForward(address, Objects.requireNonNull(loginResponse.body()).string());
        } else {
            outputJsonObject.put("success", false);
        }
        return outputJsonObject;
    }
}
