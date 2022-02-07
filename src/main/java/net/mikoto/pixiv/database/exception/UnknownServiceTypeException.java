package net.mikoto.pixiv.database.exception;

/**
 * @author mikoto
 * @date 2022/2/7 0:52
 */
public class UnknownServiceTypeException extends Exception {
    public UnknownServiceTypeException() {
        super("Unknown service type! You should use the constants in the ServiceType enumeration in the net.mikoto.pixiv.database.pojo package");
    }
}
