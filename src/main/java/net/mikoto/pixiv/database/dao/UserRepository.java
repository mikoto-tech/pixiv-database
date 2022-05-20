package net.mikoto.pixiv.database.dao;

import net.mikoto.pixiv.api.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikoto
 * @date 2022/5/21 4:54
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
}
