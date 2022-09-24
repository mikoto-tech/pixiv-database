package net.mikoto.pixiv.database.controller;

import net.mikoto.pixiv.database.dao.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mikoto
 * Create for pixiv-database
 * Create at 2022/8/17
 */
@Controller
public class H5Controller {
    private static final long ONE_HOUR = 3600;
    private final ArtworkRepository artworkRepository;
    private Long artworkTableSize;
    private Long lastGetArtworkTableSizeTime;

    @Autowired
    public H5Controller(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        if (artworkTableSize == null || (System.currentTimeMillis() - lastGetArtworkTableSizeTime) > ONE_HOUR) {
            artworkTableSize = artworkRepository.countAll();
            lastGetArtworkTableSizeTime = System.currentTimeMillis();
        }
        model.addAttribute("artworkCount", artworkTableSize);
        return "index";
    }
}
