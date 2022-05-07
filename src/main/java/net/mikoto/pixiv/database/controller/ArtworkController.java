package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import net.mikoto.pixiv.database.service.Order;
import net.mikoto.pixiv.database.service.OrderBy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            "/getArtworkByArtworkId"
    )
    public JSONObject getArtworkByArtworkId(@NotNull HttpServletResponse response, Integer artworkId) {
        response.setContentType("application/json;charset=UTF-8");
        return JSON.parseObject(JSON.toJSONString(artworkService.getArtworkByArtworkId(artworkId)));
    }

    @RequestMapping(
            "/getArtworksByKey"
    )
    public JSONObject getArtworksByKey(@NotNull HttpServletResponse response, String key, OrderBy orderBy, Order order) {
        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray();

        for (Artwork artwork :
                artworkService.getArtworks(key, orderBy, order)) {
            jsonArray.fluentAdd(JSON.parseObject(JSON.toJSONString(artwork)));
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.fluentPut("artworks", jsonArray);

        return jsonObject;
    }
}
