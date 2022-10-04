package net.mikoto.pixiv.database;

import net.mikoto.pixiv.core.client.DirectClient;
import net.mikoto.pixiv.core.connector.DirectConnector;
import net.mikoto.pixiv.core.connector.impl.independence.DirectConnectorImpl;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.service.ArtworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;

@SpringBootTest
class PixivDatabaseApplicationTests {
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private DirectClient directClient;

    @Test
    void InsertTest() throws ParseException {
        DirectConnector directConnector = new DirectConnectorImpl(directClient);
        Artwork artwork1 = directConnector.getArtwork(101601474);
        Artwork artwork2 = directConnector.getArtwork(101513081);
        Artwork artwork3 = directConnector.getArtwork(101491997);
        Artwork artwork4 = directConnector.getArtwork(101223770);
        artworkService.insertArtwork(artwork1);
        artworkService.insertArtwork(artwork2);
        artworkService.insertArtwork(artwork3);
        artworkService.insertArtwork(artwork4);
    }

    @Test
    void QueryTest1() {
        Artwork artwork = artworkService.getArtwork(101601474);
        System.out.println(artwork);
    }

    @Test
    void QueryTest2() {
        List<Artwork> artwork = artworkService.getArtworks(
                "GenshinImpact",
                2,
                "bookmark_count",
                2,
                1);
        System.out.println(artwork);
    }
}
