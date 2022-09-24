package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.core.connector.CentralConnector;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static net.mikoto.pixiv.core.constant.HttpApi.*;
import static net.mikoto.pixiv.core.constant.Scope.INSERT_ARTWORKS;

/**
 * @author mikoto
 * @date 2022/4/17 20:41
 */
@RestController
@RequestMapping(
        DATABASE_ARTWORK
)
public class ArtworkController {
    /**
     * Instances
     */
    @Qualifier("artworkRepository")
    private final ArtworkRepository artworkRepository;
    @Qualifier("centralConnector")
    private final CentralConnector centralConnector;

    @Autowired
    public ArtworkController(ArtworkRepository artworkRepository, CentralConnector centralConnector) {
        this.artworkRepository = artworkRepository;
        this.centralConnector = centralConnector;
    }

    @RequestMapping(
            value = DATABASE_ARTWORK_INSERT_ARTWORKS,
            method = RequestMethod.POST
    )
    public void insertArtworksHttpApi(@RequestBody @NotNull String data, String token) {
        JSONObject inputJsonObject = JSON.parseObject(new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8));
        JSONArray inputArtworks = inputJsonObject.getJSONArray("body");
        List<String> checkTokenResult = centralConnector.checkToken(token, INSERT_ARTWORKS);
        if (!checkTokenResult.contains(INSERT_ARTWORKS)) {
            for (Object artworkJson :
                    inputArtworks) {
                Artwork artwork = ((JSONObject) artworkJson).to(Artwork.class);
                artworkRepository.save(artwork);
            }
        }
    }

    @RequestMapping(
            DATABASE_ARTWORK_GET_ARTWORK
    )
    public JSONObject getArtworkHttpApi(@NotNull HttpServletResponse response, int artworkId) {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        Artwork artwork = artworkRepository.getArtworkByArtworkId(artworkId);
        if (artwork != null) {
            try {
                jsonObject.fluentPut("body", artwork);
                jsonObject.fluentPut("success", true);
            } catch (Exception e) {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("message", e.getMessage());
            }
        } else {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("message", "No such artwork.");
        }
        return jsonObject;
    }

    @RequestMapping(
            DATABASE_ARTWORK_GET_ARTWORKS

    )
    public JSONObject getArtworksHttpApi(@NotNull HttpServletResponse response, String credential, Sort.Direction order, @NotNull String properties, int pageCount, int grading) {
        response.setContentType("application/json;charset=UTF-8");
        Page<Artwork> artworkList;

        JSONObject jsonObject = new JSONObject();

        try {
            artworkList = artworkRepository.findArtworks(grading, credential, credential, credential, PageRequest.of(pageCount, 12, order, properties.split(";")));

            if (artworkList == null || artworkList.isEmpty()) {
                jsonObject.fluentPut("success", false);
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
        } catch (Exception e) {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("message", e.getMessage());
            return jsonObject;
        }
    }
}
