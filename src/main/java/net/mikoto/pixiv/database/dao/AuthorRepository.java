package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/5/21 4:56
 */
@Repository("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    /**
     * Get author by author id.
     *
     * @param authorId The author id.
     * @return The author.
     */
    Author getAuthorByAuthorId(Integer authorId);
}
