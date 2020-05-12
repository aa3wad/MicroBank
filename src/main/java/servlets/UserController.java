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
            String requestData = buffer.toString();
            if (path[1].equals("signUp")) {
                signUp(request, response, requestData);
                return;
            }
            else if (path[1].equals("login") && path.length == 2) {
                login(request, response, requestData);
                return;
            }
            else if (path[1].equals("logout") && path.length == 2) {
                logout(request, response);
            } else {
                sendAsJson(response, false);
            }
        }
        catch (Exception ex){
           log(" an error occurred please check with the administrator");
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response, String requestData) {
        try {
            User user = gson.fromJson(requestData, User.class);
            if (userService.AddUser(user)) {
                sendAsJson(response, true);
            } else {
                sendAsJson(response,false);
            }
        }
        catch (Exception ex){
            log(" an error occurred please check with the administrator");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response, String requestData) {
        try {
            JsonObject jsonObj = gson.fromJson(requestData, JsonObject.class);
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
        }
        catch (Exception ex){
            log(" an error occurred please check with the administrator");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        try{
            HttpSession session = request.getSession();
            session.invalidate();
            sendAsJson(response, true);
        }
        catch (Exception ex){
            log(" an error occurred please check with the administrator");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    private void sendAsJson(HttpServletResponse response, Object success) {
        try{
            Base.fixHeaders(response);
            response.setContentType("application/json");
            String result = gson.toJson(success);
            PrintWriter out = response.getWriter();
            out.print(result);
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
