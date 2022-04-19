package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
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

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            "/getArtworkByArtworkId"
    )
    public JSONObject getArtworkByArtworkId(@NotNull HttpServletResponse response, Integer artworkId) throws IllegalAccessException {
        response.setContentType("application/json;charset=UTF-8");
        return artworkService.getArtworkByArtworkId(artworkId).toJsonObject();
    }
}
