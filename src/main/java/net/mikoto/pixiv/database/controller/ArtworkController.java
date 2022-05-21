package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.api.model.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
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

import static net.mikoto.pixiv.api.http.HttpApi.*;
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
        DATABASE_ARTWORK
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
            value = DATABASE_ARTWORK_INSERT_ARTWORKS,
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void insertArtworks(@RequestBody @NotNull String data) {
        System.out.println(new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8));
    }

    @RequestMapping(
            DATABASE_ARTWORK_GET_ARTWORK
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
            DATABASE_ARTWORK_GET_ARTWORKS
    )
    public JSONObject getArtworks(@NotNull HttpServletResponse response, @NotNull String key, String credential, Sort.Direction order, String properties, int pageCount) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json;charset=UTF-8");
        if (key.equals(MAIN_PROPERTIES.getProperty(ADMIN_KEY)) || key.equals(MAIN_PROPERTIES.getProperty(ACCESS_KEY))) {

            Page<Artwork> artworkList = null;

            JSONObject jsonObject = new JSONObject();

            try {
                artworkList = artworkService.getArtworksByKey(
                        credential,
                        PageRequest.of(pageCount, 10, order, properties.split(";"))
                );
            } catch (PropertyReferenceException e) {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("body", null);
                return jsonObject;
            }

            if (artworkList == null || artworkList.isEmpty()) {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("body", null);
                return jsonObject;
            }

            JSONArray jsonArray = new JSONArray();
            for (Artwork artwork :
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
