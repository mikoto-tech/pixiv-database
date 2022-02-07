package net.mikoto.pixiv.database.dao;

import java.sql.*;

/**
 * @author mikoto
 * @date 2022/1/27 21:58
 */
public abstract class BaseDao {
    private final String url;
    private final String userName;
    private final String userPassword;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public BaseDao(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.userPassword = password;
    }

    /**
     * Get the connection.
     * If the connection is null, it will create one.
     *
     * @return Connection
     * @throws SQLException SQLException.
     */
    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url,
                    userName,
                    userPassword);
        }
        return connection;
    }

    /**
     * Query data in database.
     *
     * @param sql SQL statement.
     * @return A result set.
     * @throws SQLException SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    /**
     * Query data in database.
     *
     * @param sql SQL statement.
     * @return A result set.
     * @throws SQLException SQLException
     */
    protected boolean execute(String sql) throws SQLException {
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.execute();
    }

    /**
     * Close all the resource.
     */
    protected void closeResource() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
