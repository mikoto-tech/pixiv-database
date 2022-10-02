package net.mikoto.pixiv.database;

import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.database.dao.ArtworkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PixivDatabaseApplicationTests {
    @Autowired
    private ArtworkRepository artworkRepository;

    @Test
    void ArtworkReceiveTest() {
        Artwork artwork = artworkRepository.findById(0).get();
        System.out.println(artwork);
    }
}
