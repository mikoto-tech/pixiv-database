package net.mikoto.pixiv.database.pojo;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;

/**
 * @author mikoto
 * @date 2022/2/1 19:22
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String key;
    private String profile;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public JSONObject toJsonObject() {
        JSONObject outputJsonObject = new JSONObject();

        outputJsonObject.put("id", id);
        outputJsonObject.put("name", name);
        outputJsonObject.put("password", password);
        outputJsonObject.put("salt", salt);
        outputJsonObject.put("profile", profile);
        outputJsonObject.put("createTime", createTime);
        outputJsonObject.put("updateTime", updateTime);

        return outputJsonObject;
    }

    public User loadJson(@NotNull JSONObject jsonObject) {
        this.id = jsonObject.getInteger("id");
        this.name = jsonObject.getString("name");
        this.password = jsonObject.getString("password");
        this.salt = jsonObject.getString("salt");
        this.profile = jsonObject.getString("profile");
        this.createTime = jsonObject.getString("createTime");
        this.updateTime = jsonObject.getString("updateTime");

        return this;
    }
}
