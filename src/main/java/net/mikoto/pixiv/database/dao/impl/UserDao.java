package net.mikoto.pixiv.database.dao.impl;

import net.mikoto.pixiv.api.pojo.User;
import net.mikoto.pixiv.database.dao.BaseDao;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2022/2/7 21:40
 */
public class UserDao extends BaseDao {
    private static final String RESULT = "result";

    public UserDao(String url, String userName, String password) {
        super(url, userName, password);
    }

    public boolean addUser(@NotNull User user) throws SQLException {
        Connection connection = getConnection();
        String sql = "INSERT INTO pixiv_web_data.user_data (user_name, user_password, user_salt, user_key, profile_url, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getUserPassword());
        preparedStatement.setString(3, user.getUserSalt());
        preparedStatement.setString(4, user.getUserKey());
        preparedStatement.setString(5, user.getProfileUrl());
        preparedStatement.setString(6, user.getCreateTime());
        preparedStatement.setString(7, user.getUpdateTime());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        return true;
    }

    public void updateUserName(int userId, String newUserName, String updateTime) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE pixiv_web_data.user_data t SET t.user_name = ?, t.update_time = ? WHERE t.pk_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newUserName);
        preparedStatement.setString(2, updateTime);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateUserPassword(int userId, String newUserPassword, String updateTime) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE pixiv_web_data.user_data t SET t.user_password = ?, t.update_time = ? WHERE t.pk_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newUserPassword);
        preparedStatement.setString(2, updateTime);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateUserSalt(int userId, String newUserSalt, String updateTime) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE pixiv_web_data.user_data t SET t.user_salt = ?, t.update_time = ? WHERE t.pk_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newUserSalt);
        preparedStatement.setString(2, updateTime);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateUserKey(int userId, String newUserKey, String updateTime) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE pixiv_web_data.user_data t SET t.user_key = ?, t.update_time = ? WHERE t.pk_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newUserKey);
        preparedStatement.setString(2, updateTime);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateProfileUrl(int userId, String newProfileUrl, String updateTime) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE pixiv_web_data.user_data t SET t.profile_url = ?, t.update_time = ? WHERE t.pk_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newProfileUrl);
        preparedStatement.setString(2, updateTime);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public User getUser(@NotNull String userName) throws SQLException {
        User user = new User();
        String sql = "SELECT * FROM pixiv_web_data.user_data WHERE user_name='" + userName + "'";
        return getUser(user, sql);
    }

    public User getUser(int userId) throws SQLException {
        User user = new User();
        String sql = "SELECT * FROM pixiv_web_data.user_data WHERE pk_id='" + userId + "'";
        return getUser(user, sql);
    }

    public User getUserByKey(String userKey) throws SQLException {
        User user = new User();
        String sql = "SELECT * FROM pixiv_web_data.user_data WHERE user_key='" + userKey + "'";
        return getUser(user, sql);
    }

    private User getUser(User user, String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        if (resultSet.next()) {
            user.setId(resultSet.getInt("pk_id"));
            user.setUserName(resultSet.getString("user_name"));
            user.setUserPassword(resultSet.getString("user_password"));
            user.setUserSalt(resultSet.getString("user_salt"));
            user.setUserKey(resultSet.getString("user_key"));
            user.setProfileUrl(resultSet.getString("profile_url"));
            user.setCreateTime(resultSet.getDate("create_time").toString());
            user.setUpdateTime(resultSet.getDate("update_time").toString());
        }

        return user;
    }

    public boolean checkUserName(String userName) throws SQLException {
        String sql = "select count(*) as result from pixiv_web_data.user_data where user_name='" + userName + "';";
        ResultSet resultSet = executeQuery(sql);
        return !resultSet.next() || resultSet.getInt(RESULT) == 0;
    }
}
