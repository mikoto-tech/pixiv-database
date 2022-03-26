package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.http.database.central.*;
import net.mikoto.pixiv.api.pojo.User;
import net.mikoto.pixiv.database.service.impl.UserServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static net.mikoto.pixiv.api.http.HttpApi.*;
import static net.mikoto.pixiv.database.constant.Properties.MAIN_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/2/3 7:18
 * 对此处PixivCentralDataBase服务的错误码说明:
 * A -- 客户端问题
 * B -- 服务器问题
 * C -- 第三方服务问题
 * 以下为本API的错误码对照表:
 * A0001 -- 错误的CENTRAL_CONNECTOR_KEY
 * A0002 -- 需要添加的用户的用户名已存在
 * A0003 -- 需要移除的Token不存在或权限不足无法移除
 * A0004 -- 查找的用户不存在
 * <p>
 * B0001 -- 未知数据库错误
 */
@RestController
public class CentralController implements InsertUser, GetUserByUserName, GetUserByUserId, GetUserByUserKey, UpdateUserName, UpdateUserProfile, UpdateUserPassword, UpdateUserKey {
    private static final String CENTRAL_CONNECTOR_KEY = "CENTRAL_CONNECTOR_KEY";

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_INSERT_USER,
            method = RequestMethod.GET
    )
    @Override
    public void insertUserHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userName,
            @RequestParam String userPassword,
            @RequestParam String userSalt,
            @RequestParam String userKey,
            @RequestParam String profileUrl,
            @RequestParam String createTime,
            @RequestParam String updateTime
    ) throws IOException, SQLException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            if (UserServiceImpl.getInstance().checkUserName(userName)) {
                User user = new User();
                user.setUserName(userName);
                user.setUserPassword(userPassword);
                user.setUserSalt(userSalt);
                user.setUserKey(userKey);
                user.setProfileUrl(profileUrl);
                user.setCreateTime(createTime);
                user.setUpdateTime(updateTime);
                if (UserServiceImpl.getInstance().addUser(user)) {
                    outputJsonObject.put("success", true);
                    outputJsonObject.put("code", "00000");
                } else {
                    outputJsonObject.put("success", false);
                    outputJsonObject.put("code", "B0001");
                }
            } else {
                outputJsonObject.put("success", false);
                outputJsonObject.put("code", "A0002");
            }
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_GET_USER_BY_USER_NAME,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject getUserByUserNameHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam String key,
            @RequestParam String userName
    ) throws IOException, SQLException, IllegalAccessException {
        return outputUser(response, UserServiceImpl.getInstance().getUser(userName), key);
    }

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_GET_USER_BY_USER_ID,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject getUserByUserIdHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userId
    ) throws IOException, SQLException, IllegalAccessException {
        return outputUser(response, UserServiceImpl.getInstance().getUser(Integer.parseInt(userId)), key);
    }

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_GET_USER_BY_USER_KEY,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject getUserByUserKeyHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam @NotNull String userKey
    ) throws IOException, SQLException, IllegalAccessException {
        return outputUser(response, UserServiceImpl.getInstance().getUserByKey(userKey), key);
    }

    @Override
    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_UPDATE_USER_NAME,
            method = RequestMethod.GET
    )
    public JSONObject updateUserNameHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userId,
            @RequestParam String newUserName
    ) throws SQLException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserServiceImpl.getInstance().changeName(Integer.parseInt(userId), newUserName);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        return outputJsonObject;
    }

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_UPDATE_USER_PASSWORD,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject updateUserPasswordHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userId,
            @RequestParam String newUserPassword,
            @RequestParam String newUserSalt
    ) throws SQLException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserServiceImpl.getInstance().changePassword(Integer.parseInt(userId), newUserPassword, newUserSalt);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        return outputJsonObject;
    }

    @Override
    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_UPDATE_USER_KEY,
            method = RequestMethod.GET
    )
    public JSONObject updateUserKeyHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userId,
            @RequestParam String newUserKey
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserServiceImpl.getInstance().changeKey(Integer.parseInt(userId), newUserKey);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        return outputJsonObject;
    }

    @RequestMapping(
            value = DATABASE_CENTRAL + DATABASE_CENTRAL_UPDATE_USER_PROFILE,
            method = RequestMethod.GET
    )
    @Override
    public JSONObject updateUserProfileHttpApi(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam String userId,
            @RequestParam String newUserProfile
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserServiceImpl.getInstance().changeProfile(Integer.parseInt(userId), newUserProfile);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        return outputJsonObject;
    }

    private @NotNull JSONObject outputUser(@NotNull HttpServletResponse response, User user, @NotNull String key) throws IOException, IllegalAccessException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(MAIN_PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            if (user.getUserName() != null) {
                outputJsonObject.put("success", true);
                outputJsonObject.put("code", "00000");
                outputJsonObject.put("body", user.toJsonObject());
            } else {
                outputJsonObject.put("success", false);
                outputJsonObject.put("code", "A0004");
            }
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        return outputJsonObject;
    }
}
