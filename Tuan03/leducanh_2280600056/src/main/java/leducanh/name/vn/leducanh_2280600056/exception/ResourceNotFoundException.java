package leducanh.name.vn.leducanh_2280600056.exception;

/**
 * Exception thrown when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tìm thấy với %s : '%s'", resourceName, fieldName, fieldValue));
    }
}