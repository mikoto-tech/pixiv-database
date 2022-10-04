package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mikoto
 * @date 2022/4/17 20:41
 */
@RestController
@RequestMapping(
        "/artwork"
)
public class ArtworkController {
    /**
     * Instances
     */
    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            "/getArtwork"
    )
    public JSONObject getArtworkHttpApi(@NotNull HttpServletResponse response, int artworkId) {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        Artwork artwork = artworkService.getArtwork(artworkId);
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
            "/getArtworks"
    )
    public JSONObject getArtworksHttpApi(@NotNull HttpServletResponse response, String tag, @NotNull String properties, int pageCount, int grading, int size) {
        if (size == 0) {
            size = 12;
        }

        response.setContentType("application/json;charset=UTF-8");
        List<Artwork> artworkList;

        JSONObject jsonObject = new JSONObject();

        try {
            if (size <= 30) {
                artworkList = artworkService.getArtworks(
                        tag,
                        grading,
                        properties,
                        size,
                        pageCount
                );
                if (!artworkList.isEmpty()) {
                    jsonObject.fluentPut("success", true);
                    jsonObject.fluentPut("body", artworkList);
                } else {
                    jsonObject.fluentPut("success", false);
                }
            } else {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("message", "Size too long");
            }

            return jsonObject;
        } catch (Exception e) {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("message", e.getMessage());
            return jsonObject;
        }
    }
}
