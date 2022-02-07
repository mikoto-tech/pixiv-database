package net.mikoto.pixiv.database.service;

import net.mikoto.pixiv.database.pojo.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.USER_DAO;

/**
 * @author mikoto
 * @date 2022/2/8 0:22
 */
public class UserService {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public boolean addUser(User user) throws SQLException {
        return USER_DAO.addUser(user);
    }

    public User getUser(String userName) throws SQLException {
        return USER_DAO.getUser(userName);
    }

    public User getUser(int userId) throws SQLException {
        return USER_DAO.getUser(userId);
    }

    public void changePassword(int userId, String newUserPassword, String newUserSalt) throws SQLException {
        USER_DAO.updateUserPassword(userId, newUserPassword, SIMPLE_DATE_FORMAT.format(new Date()));
        USER_DAO.updateUserSalt(userId, newUserSalt, SIMPLE_DATE_FORMAT.format(new Date()));
    }

    public void changeKey(int userId, String newUserKey) throws SQLException {
        USER_DAO.updateUserKey(userId, newUserKey, SIMPLE_DATE_FORMAT.format(new Date()));
    }

    public void changeName(int userId, String newUserName) throws SQLException {
        USER_DAO.updateUserName(userId, newUserName, SIMPLE_DATE_FORMAT.format(new Date()));
    }

    public void changeProfile(int userId, String newUserProfile) throws SQLException {
        USER_DAO.updateProfileUrl(userId, newUserProfile, SIMPLE_DATE_FORMAT.format(new Date()));
    }

    public boolean checkUserName(String userName) throws SQLException {
        return USER_DAO.checkUserName(userName);
    }
}
