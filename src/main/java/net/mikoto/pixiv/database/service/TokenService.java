package net.mikoto.pixiv.database.service;

import net.mikoto.pixiv.database.exception.UnknownServiceTypeException;
import net.mikoto.pixiv.database.pojo.ServiceType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/2/6 6:54
 */
public class TokenService {
    private static final TokenService INSTANCE = new TokenService();

    private final Set<String> forwardTokenSet = new HashSet<>();
    private final Set<String> patcherTokenSet = new HashSet<>();
    private final Set<String> displayerTokenSet = new HashSet<>();

    /**
     * Singleton.
     *
     * @return This object.
     */
    public static TokenService getInstance() {
        return INSTANCE;
    }

    /**
     * Add token into the set.
     *
     * @param serviceType The type of the service.
     * @param token       Token string.
     * @throws UnknownServiceTypeException Throw this exception if this method cannot find the type of this service.
     */
    public void addToken(@NotNull ServiceType serviceType, @NotNull String token) throws UnknownServiceTypeException {
        switch (serviceType) {
            case PATCHER -> patcherTokenSet.add(token);
            case FORWARD -> forwardTokenSet.add(token);
            case DISPLAYER -> displayerTokenSet.add(token);
            default -> throw new UnknownServiceTypeException();
        }
    }

    /**
     * Remove token from the set.
     *
     * @param serviceType The type of the service.
     * @param token       Token string.
     * @throws UnknownServiceTypeException Throw this exception if this method cannot find the type of this service.
     */
    public boolean removeToken(@NotNull ServiceType serviceType, @NotNull String token) throws UnknownServiceTypeException {
        switch (serviceType) {
            case PATCHER -> {
                return patcherTokenSet.remove(token);
            }
            case FORWARD -> {
                return forwardTokenSet.remove(token);
            }
            case DISPLAYER -> {
                return displayerTokenSet.remove(token);
            }
            default -> throw new UnknownServiceTypeException();
        }
    }

    /**
     * To know whether the token is contained in the set or not.
     *
     * @param serviceType The type of the service.
     * @param token       Token string.
     * @throws UnknownServiceTypeException Throw this exception if this method cannot find the type of this service.
     */
    public boolean confirmToken(@NotNull ServiceType serviceType, @NotNull String token) throws UnknownServiceTypeException {
        switch (serviceType) {
            case PATCHER -> {
                return patcherTokenSet.contains(token);
            }
            case FORWARD -> {
                return forwardTokenSet.contains(token);
            }
            case DISPLAYER -> {
                return displayerTokenSet.contains(token);
            }
            default -> throw new UnknownServiceTypeException();
        }
    }
}
