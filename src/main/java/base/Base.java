package base;

import com.google.gson.Gson;
import models.Account;
import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Base {

    public static HashMap<String , Account> accounts = new HashMap<>();

    public static void fillAccounts() {
        List<User> users = Arrays.asList(
                new User("omar", "333", "123"),
                new User("ahmad", "111", "123")
                );
        List<Account> accountList = Arrays.asList(
                new Account("Saving", "111", users.get(0)),
                new Account("Checking", "333", users.get(1))
                );

        if(accounts.values().isEmpty()) {
            for (Account account : accountList) {
                accounts.put(account.getAccountNumber(), account);
            }
        }
    }

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
