package servlets;

import com.google.gson.Gson;
import services.AccountService;
import services.TransactionService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TransactionController", urlPatterns = "/transaction")
public class TransactionController extends HttpServlet {

    private TransactionService transactionService;
    private Gson gson;

    public TransactionController() {

    }

    public void init(){
        transactionService = new TransactionService();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
