package base;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Base {

    public static HttpServletResponse fixHeaders(HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setContentType("application/json");

        return response;
    }

    public static String ReadRequestBody(HttpServletRequest request) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String requestBody = buffer.toString();

        return requestBody;
    }

    public static void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new Gson();
        response = Base.fixHeaders(response);

        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
