package cn.enncy.funny.constant;


import org.springframework.http.HttpStatus;

/**
 * 响应码中文翻译
 *
 * @author: enncy
 */
public enum HttpErrorStateConverter {

    /* 客户端错误 */
    BAD_REQUEST(400,  "由于包含语法错误，当前请求无法被服务器理解"),

    UNAUTHORIZED(401,  "请求需要用户验证"),

    PAYMENT_REQUIRED(402,  "非标准客户端错误状态响应码"),

    FORBIDDEN(403,  "您的权限不足,禁止访问"),

    NOT_FOUND(404,  "访问的资源不存在"),

    METHOD_NOT_ALLOWED(405,  "不支持的请求方法"),

    NOT_ACCEPTABLE(406,  "请求的资源的内容无法满足请求头中的条件"),

    PROXY_AUTHENTICATION_REQUIRED(407,  "客户端必须在代理服务器上进行身份验证"),

    REQUEST_TIMEOUT(408,  "请求超时"),

    CONFLICT(409,  "和被请求的资源的当前状态之间存在冲突"),

    GONE(410,  "被请求的资源在服务器上已经不再可用"),

    LENGTH_REQUIRED(411,  "没有定义 content-length 请求头"),

    PRECONDITION_FAILED(412,  "先决条件失败"),

    PAYLOAD_TOO_LARGE(413,  "请求提交的实体数据长度超出限制"),

    REQUEST_ENTITY_TOO_LARGE(413,  "请求提交的实体数据长度超出限制"),

    URI_TOO_LONG(414,  "请求的URI长度超过了服务器能够解释的长度"),

    REQUEST_URI_TOO_LONG(414,  "请求的URI长度超过了服务器能够解释的长度"),

    UNSUPPORTED_MEDIA_TYPE(415,  "不支持的请求资源"),

    REQUESTED_RANGE_NOT_SATISFIABLE(416,  "请求指定的所有数据范围的首字节位置超过了当前资源的长度"),

    EXPECTATION_FAILED(417,  "内容无法被满足"),

    I_AM_A_TEAPOT(418,  "本操作码是在1998年作为IETF的传统愚人节笑话, 在RFC 2324 超文本咖啡壶控制协议中定义的，并不需要在真实的HTTP服务器中定义。"),

    UNPROCESSABLE_ENTITY(422,  "请求格式正确，但是由于含有语义错误，无法响应"),

    LOCKED(423,  "当前资源被锁定"),

    FAILED_DEPENDENCY(424,  "由于之前的某个请求发生的错误，导致当前请求失败"),

    TOO_EARLY(425,  "请求事件过早"),

    UPGRADE_REQUIRED(426,  "协议需要升级"),

    PRECONDITION_REQUIRED(428,  "先决条件要求为满足"),

    TOO_MANY_REQUESTS(429,  "请求过多"),

    REQUEST_HEADER_FIELDS_TOO_LARGE(431,  "请求头字段长度太大"),

    UNAVAILABLE_FOR_LEGAL_REASONS(451,  "该请求因法律原因不可用"),

    /* 服务端错误 */
    INTERNAL_SERVER_ERROR(500, "服务器内部发生未知错误"),

    NOT_IMPLEMENTED(501, "此资源正在进行处理"),

    BAD_GATEWAY(502, "服务器网关错误"),

    SERVICE_UNAVAILABLE(503, "该服务不可用"),

    GATEWAY_TIMEOUT(504, "网关超时"),

    HTTP_VERSION_NOT_SUPPORTED(505, "此 HTTP 版本不受支持"),

    VARIANT_ALSO_NEGOTIATES(506, "服务器存在内部配置错误"),

    INSUFFICIENT_STORAGE(507, "服务器达到带宽限制"),

    LOOP_DETECTED(508, "检测到循环链接错误"),

    BANDWIDTH_LIMIT_EXCEEDED(509, "超出带宽限制"),

    NOT_EXTENDED(510, "获取资源所需要的策略并没有被满足"),

    NETWORK_AUTHENTICATION_REQUIRED(511, "需要网络身份验证");

    private static final HttpErrorStateConverter[] VALUES;

    static {
        VALUES = values();
    }


    public int statusCode;
    public HttpStatus status;
    public String description;

    HttpErrorStateConverter(int statusCode, String description) {
        this.statusCode = statusCode;
        this.status = HttpStatus.resolve(statusCode);

        if(this.status!=null){
            this.description = status.toString() + " "+ description;
        }else{
            this.description = description;
        }
    }


    public static HttpErrorStateConverter resolve(int statusCode){
        for (HttpErrorStateConverter converter : VALUES) {
            if (converter.statusCode  == statusCode) {
                return converter;
            }
        }
        return null;
    }


}
