package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import net.mikoto.pixiv.database.service.FindBy;
import net.mikoto.pixiv.database.service.Order;
import net.mikoto.pixiv.database.service.OrderBy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

import static net.mikoto.pixiv.api.util.RsaUtil.getPrivateKey;
import static net.mikoto.pixiv.api.util.RsaUtil.sign;
import static net.mikoto.pixiv.api.util.Sha256Util.getSha256;
import static net.mikoto.pixiv.database.constant.Constant.*;

/**
 * @author mikoto
 * @date 2022/4/17 20:41
 */
@RestController
@RequestMapping(
        "/api/artwork"
)
public class ArtworkController {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;

    private static final String KEY = "key";

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            value = "/insertArtworks",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void insertArtworks(@RequestBody @NotNull String data) {
        System.out.println(new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8));
    }

    @RequestMapping(
            "/getArtwork"
    )
    public JSONObject getArtwork(@NotNull HttpServletResponse response, @NotNull String key, Integer artworkId) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        response.setContentType("application/json;charset=UTF-8");
        if (key.equals(MAIN_PROPERTIES.getProperty(ADMIN_KEY)) || key.equals(MAIN_PROPERTIES.getProperty(ACCESS_KEY))) {
            JSONObject jsonObject = new JSONObject();
            Artwork artwork = artworkService.getArtworkByArtworkId(artworkId);
            if (artwork != null) {
                jsonObject.fluentPut("body", artwork);
                jsonObject.fluentPut("sign", sign(getSha256(jsonObject.getJSONObject("body").toJSONString()), getPrivateKey(MAIN_PROPERTIES.getProperty(RSA_PRIVATE_KEY))));
                jsonObject.fluentPut("success", true);
                jsonObject.fluentPut("message", "");
            } else {
                jsonObject.fluentPut("body", null);
                jsonObject.fluentPut("sign", null);
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("message", "Null artwork");
            }
            return jsonObject;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", "Wrong key!");
            return jsonObject;
        }
    }

    @RequestMapping(
            "/getArtworks"
    )
    public JSONObject getArtworks(@NotNull HttpServletResponse response, @NotNull String key, String credential, FindBy findBy, OrderBy orderBy, Order order) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json;charset=UTF-8");
        if (key.equals(MAIN_PROPERTIES.getProperty(ADMIN_KEY)) || key.equals(MAIN_PROPERTIES.getProperty(ACCESS_KEY))) {
            JSONObject jsonObject = new JSONObject();

            List<?> artworkList = null;

            if (findBy == FindBy.SeriesId) {
                artworkList = artworkService.getArtworks(findBy, orderBy, order, Integer.parseInt(credential));
            } else if (findBy == FindBy.Key) {
                artworkList = artworkService.getArtworks(findBy, orderBy, order, credential);
            }

            if (artworkList == null) {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("body", null);
                return jsonObject;
            }

            JSONArray jsonArray = new JSONArray();
            for (Object artwork :
                    artworkList) {
                jsonArray.fluentAdd(artwork);
            }
            jsonObject.fluentPut("success", true);
            jsonObject.fluentPut("body", jsonArray);
            return jsonObject;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", "Wrong key!");
            return jsonObject;
        }
    }
}
