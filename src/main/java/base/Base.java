package base;

import javax.servlet.http.HttpServletResponse;

public class Base {

    public static HttpServletResponse fixHeaders(HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setContentType("application/json");

        return response;
    }
}
