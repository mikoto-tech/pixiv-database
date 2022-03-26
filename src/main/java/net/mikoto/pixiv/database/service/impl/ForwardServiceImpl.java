package net.mikoto.pixiv.database.service.impl;

import net.mikoto.pixiv.database.service.ForwardService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mikoto
 * @date 2022/3/19 21:38
 */
public class ForwardServiceImpl implements ForwardService {
    private static final Map<String, String> FORWARD_SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * Insert a Pixiv-Forward service.
     *
     * @param address   The address of the service.
     * @param publicKey The public key of the service.
     */
    @Override
    public void insertForward(String address, String publicKey) {
        FORWARD_SERVICE_MAP.put(address, publicKey);
    }

    /**
     * Get a Pixiv-Forward service.
     *
     * @param address The address of the service.
     * @return Public key.
     */
    @Override
    public String getForward(String address) {
        return FORWARD_SERVICE_MAP.get(address);
    }

    /**
     * Remove a Pixiv-Forward service.
     *
     * @param address The address of the service.
     */
    @Override
    public void removeForward(String address) {
        FORWARD_SERVICE_MAP.remove(address);
    }
}
