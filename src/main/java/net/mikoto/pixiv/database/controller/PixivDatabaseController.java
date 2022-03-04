package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mikoto
 * @date 2022/3/5 3:40
 */
@RestController
public class PixivDatabaseController {
    @RequestMapping(
            value = "/database",
            method = RequestMethod.GET
    )
    public String database(@NotNull HttpServletResponse response) {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        JSONObject outputJsonObject = new JSONObject();

        outputJsonObject.put("success", true);
        return outputJsonObject.toJSONString();
    }
}
