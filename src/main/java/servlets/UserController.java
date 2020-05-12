package servlets;

import base.Base;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Account;
import models.User;
import services.AccountService;
import services.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;

@WebServlet(name = "UserController", urlPatterns = "/user/*")
public class UserController extends HttpServlet {

    private UserService userService;
    private AccountService accountService;
    private Gson gson;

    public UserController() {

    }
    public void init(){
        userService = new UserService();
        accountService = new AccountService();
        gson = new Gson();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
            String pathInfo = request.getPathInfo();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String[] path = pathInfo.split("/");
            String payload = buffer.toString();
            if (pathInfo.equals("") || pathInfo.equals("/")) {
                User user = gson.fromJson(payload, User.class);
                if (userService.AddUser(user)) {
                    sendAsJson(response, true);
                } else {
                    String error = "{message: user already exists}";
                    gson.toJson(error);
                    sendAsJson(response, error);
                }
                return;
            }
            if (path[1].equals("login") && path.length == 2) {
                JsonObject jsonObj = gson.fromJson(payload, JsonObject.class);
                String username = jsonObj.get("userName").getAsString();
                String password = jsonObj.get("password").getAsString();
                boolean valid = false;
                if (userService.validateUser(username, password)) {
                    String accountNumber = userService.getAccountNumber(username);
                    Account account = accountService.FindAccount(accountNumber);
                    request.getSession().setAttribute("account", account);
                    valid = true;
                }
                sendAsJson(response, valid);
                return;
            }
            if (path[1].equals("logout") && path.length == 2) {
                HttpSession session = request.getSession();
                session.invalidate();
                String error = "{you have logout successfully!}";
                gson.toJson(error);
                sendAsJson(response, error);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        catch (Exception ex){
           log(" an error occurred please check with the administrator");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    private void sendAsJson(HttpServletResponse response, Object obj) {
        try{
            Base.fixHeaders(response);
            response.setContentType("application/json");
            String res = gson.toJson(obj);
            PrintWriter out = response.getWriter();
            out.print(res);
            out.flush();
        }
        catch (Exception ex){
            log(" an error occurred please check with the administrator");
        }
    }



    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse response) {
        try{
            Base.fixHeaders(response);
        }
        catch (Exception ex){
            log(" an error occurred please check with the administrator");
        }
    }
}
