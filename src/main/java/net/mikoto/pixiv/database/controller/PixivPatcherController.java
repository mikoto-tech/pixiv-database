package net.mikoto.pixiv.database.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.pojo.PixivData;
import net.mikoto.pixiv.database.pojo.ServiceType;
import net.mikoto.pixiv.database.service.PixivDataService;
import net.mikoto.pixiv.database.service.TokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2022/2/7 10:05
 */
@RestController
public class PixivPatcherController {
    @RequestMapping(
            value = "/patcher/addPixivData",
            method = RequestMethod.POST
    )
    public void addPixivData(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @RequestParam @NotNull String token
    ) throws IOException, UnknownServiceTypeException, SQLException {
        // SetHeader
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // InitVariable
        PrintWriter out = response.getWriter();
        JSONObject outputJsonObject = new JSONObject();

        // Verify token
        if (TokenService.getInstance().confirmToken(ServiceType.PATCHER, token)) {
            // Load data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            PixivData pixivData = new PixivData();
            pixivData.loadJson(JSON.parseObject(stringBuilder.toString()));

            // Add pixiv data
            PixivDataService.getInstance().addPixivData(pixivData);

            outputJsonObject.put("success", true);
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "Wrong token.");
        }

        // output
        out.println(outputJsonObject.toJSONString());
    }
}
