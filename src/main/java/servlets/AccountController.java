package servlets;

import com.google.gson.Gson;
import services.AccountService;
import services.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountController", urlPatterns = "/account")
public class AccountController extends HttpServlet {

    private AccountService accountService;
    private Gson gson;

    public AccountController() {

    }

    public void init(){
        accountService = new AccountService();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
