package sa.com.moath.caching.configuration;

public class ContextHolder {

    private static final ThreadLocal<RequestContext> requestHolder = new ThreadLocal<>();

    public static RequestContext initialize() {
        RequestContext requestContext = new RequestContext();
        set(requestContext);
        return requestContext;
    }

    public static RequestContext get() {
        return requestHolder.get();
    }

    public static RequestContext safeGet(){
        RequestContext requestContext = requestHolder.get();
        if (requestContext == null) {
            requestContext = new RequestContext();
            set(requestContext);
        }
        return requestContext;
    }

    public static void set(RequestContext context) {
        requestHolder.set(context);
    }

    public static void unset() {
        requestHolder.remove();
    }



}
