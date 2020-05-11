package servlets;

import com.google.gson.Gson;
import services.AccountService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = "/user")
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
