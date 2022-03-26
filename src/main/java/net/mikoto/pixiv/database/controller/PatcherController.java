package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.http.database.patcher.InsertPixivData;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.service.PixivDataService;
import net.mikoto.pixiv.database.service.impl.ArtworkServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_PATCHER;
import static net.mikoto.pixiv.api.http.HttpApi.DATABASE_PATCHER_INSERT_PIXIV_DATA;
import static net.mikoto.pixiv.database.constant.ForwardService.FORWARD_SERVICE;
import static net.mikoto.pixiv.database.util.RsaUtil.getPublicKey;
import static net.mikoto.pixiv.database.util.RsaUtil.verify;

/**
 * @author mikoto
 * @date 2022/2/7 10:05
 */
@RestController
public class PatcherController implements InsertPixivData {
    private static final PixivDataService PIXIV_DATA_SERVICE = new ArtworkServiceImpl();

    @RequestMapping(
            value = DATABASE_PATCHER + DATABASE_PATCHER_INSERT_PIXIV_DATA,
            method = RequestMethod.POST
    )
    @Override
    public JSONObject insertPixivDataHttpApi(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @RequestParam String address,
            @RequestParam String sign
    ) throws IOException, UnknownServiceTypeException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        JSONObject outputJsonObject = new JSONObject();

        // Load data
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        JSONObject data = JSON.parseObject(stringBuilder.toString());

        // Verify token
        if (verify(data.toJSONString(), getPublicKey(FORWARD_SERVICE.getForward(address)), sign)) {

            Artwork artwork = new Artwork();
            artwork.loadJson(data);

            // Add pixiv data
            PIXIV_DATA_SERVICE.insertArtwork(artwork);

            outputJsonObject.put("success", true);
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "Wrong token.");
        }

        return outputJsonObject;
    }
}
