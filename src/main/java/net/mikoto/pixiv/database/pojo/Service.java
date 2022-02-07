package net.mikoto.pixiv.database.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * @author mikoto
 * @date 2022/2/1 19:28
 */
public class Service {
    private int id;
    private ServiceType serviceType;
    private String address;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        outputJsonObject.put("serviceType", serviceType);
        outputJsonObject.put("address", address);
        outputJsonObject.put("createTime", createTime);
        outputJsonObject.put("updateTime", updateTime);

        return outputJsonObject;
    }
}
