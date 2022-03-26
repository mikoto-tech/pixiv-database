package net.mikoto.pixiv.database.service;

/**
 * @author mikoto
 * @date 2022/3/19 21:38
 */
public interface ForwardService {
    /**
     * Insert a Pixiv-Forward service.
     *
     * @param address   The address of the service.
     * @param publicKey The public key of the service.
     */
    void insertForward(String address, String publicKey);

    /**
     * Get a Pixiv-Forward service.
     *
     * @param address The address of the service.
     * @return Public key.
     */
    String getForward(String address);

    /**
     * Remove a Pixiv-Forward service.
     *
     * @param address The address of the service.
     */
    void removeForward(String address);
}
