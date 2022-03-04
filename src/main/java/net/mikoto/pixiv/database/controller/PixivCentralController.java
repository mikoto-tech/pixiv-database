package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.pojo.ServiceType;
import net.mikoto.pixiv.api.pojo.User;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.service.TokenService;
import net.mikoto.pixiv.database.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static net.mikoto.pixiv.database.PixivDatabaseApplication.PROPERTIES;

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
public class PixivCentralController {
    private static final String CENTRAL_CONNECTOR_KEY = "CENTRAL_CONNECTOR_KEY";

    @RequestMapping(
            value = "/central/addToken",
            method = RequestMethod.GET
    )
    public void addToken(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam @NotNull ServiceType serviceType,
            @RequestParam @NotNull String token
    ) throws IOException, UnknownServiceTypeException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
            TokenService.getInstance().addToken(serviceType, token);
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = "/central/removeToken",
            method = RequestMethod.GET
    )
    public void removeToken(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam @NotNull ServiceType serviceType,
            @RequestParam @NotNull String token
    ) throws IOException, UnknownServiceTypeException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            if (TokenService.getInstance().removeToken(serviceType, token)) {
                outputJsonObject.put("success", true);
                outputJsonObject.put("code", "00000");
            } else {
                outputJsonObject.put("success", false);
                outputJsonObject.put("code", "A0003");
            }
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = "/central/insertUser",
            method = RequestMethod.GET
    )
    public void insertUser(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String userPassword,
            @RequestParam @NotNull String userSalt,
            @RequestParam @NotNull String userKey,
            @RequestParam @NotNull String profileUrl,
            @RequestParam @NotNull String createTime,
            @RequestParam @NotNull String updateTime
    ) throws IOException, SQLException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            if (UserService.getInstance().checkUserName(userName)) {
                User user = new User();
                user.setName(userName);
                user.setPassword(userPassword);
                user.setSalt(userSalt);
                user.setKey(userKey);
                user.setProfile(profileUrl);
                user.setCreateTime(createTime);
                user.setUpdateTime(updateTime);
                if (UserService.getInstance().addUser(user)) {
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
            value = "/central/getUserByUserName",
            method = RequestMethod.GET
    )
    public void getUser(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam @NotNull String userName
    ) throws IOException, SQLException {
        outputUser(response, UserService.getInstance().getUser(userName), key);
    }

    @RequestMapping(
            value = "/central/getUserByUserId",
            method = RequestMethod.GET
    )
    public void getUser(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam int userId
    ) throws IOException, SQLException {
        outputUser(response, UserService.getInstance().getUser(userId), key);
    }

    @RequestMapping(
            value = "/central/changeUserName",
            method = RequestMethod.GET
    )
    public void changeUserName(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam int userId,
            @RequestParam String newUserName
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserService.getInstance().changeName(userId, newUserName);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = "/central/changeUserPassword",
            method = RequestMethod.GET
    )
    public void changeUserPassword(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam int userId,
            @RequestParam String newUserPassword,
            @RequestParam String newUserSalt
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserService.getInstance().changePassword(userId, newUserPassword, newUserSalt);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = "/central/changeUserKey",
            method = RequestMethod.GET
    )
    public void changeUserKey(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam int userId,
            @RequestParam String newUserKey
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserService.getInstance().changeKey(userId, newUserKey);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    @RequestMapping(
            value = "/central/changeUserProfile",
            method = RequestMethod.GET
    )
    public void changeUserProfile(
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String key,
            @RequestParam int userId,
            @RequestParam String newUserProfile
    ) throws SQLException, IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            UserService.getInstance().changeProfile(userId, newUserProfile);
            outputJsonObject.put("success", true);
            outputJsonObject.put("code", "00000");
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("code", "A0001");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }

    private void outputUser(@NotNull HttpServletResponse response, User user, @NotNull String key) throws IOException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Confirm key
        if (key.equals(PROPERTIES.getProperty(CENTRAL_CONNECTOR_KEY))) {
            if (user.getName() != null) {
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
        out.println(outputJsonObject.toJSONString());
    }
}
