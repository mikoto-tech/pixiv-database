package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/5/21 4:54
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Get user by user id.
     *
     * @param userId The user id.
     * @return The user.
     */
    User getUserByUserId(Integer userId);
}
