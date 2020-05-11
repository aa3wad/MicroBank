package servlets;

import base.Base;
import base.ResponseResult;
import base.Status;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Account;
import services.AccountService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "AccountController", urlPatterns = "/accounts/*")
public class AccountController extends HttpServlet {

    private AccountService accountService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountService = new AccountService();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseResult responseResult;
        String pathInfo = request.getPathInfo();
        String[] path = pathInfo.split("/");

        String requestBody = Base.ReadRequestBody(request);
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);

        if (pathInfo.equals("/") || path[1].equals("save")) {
            Account account = gson.fromJson(requestBody, Account.class);
            String errorMessage = accountService.createAccount(account);
            responseResult = new ResponseResult<Account>(errorMessage, Status.Success, account);
            Base.sendAsJson(response, responseResult);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ResponseResult responseResult;
        String pathInfo = request.getPathInfo();
        String[] path = pathInfo.split("/");

        //in case pathInfo == "/" then return all accounts
        if(pathInfo.equals("/")){
            Collection<Account> accounts = accountService.getAllAccounts().values();
            responseResult = new ResponseResult<Collection<Account>>("all accounts", Status.Success, accounts);
            Base.sendAsJson(response, responseResult);
            return;
        }
        else if(path.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //In case path[1] has value then return specific account
        String accountNumber = path[1];
        if(!accountNumber.isEmpty()) {
            if (!accountService.getAllAccounts().containsKey(accountNumber)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else {
                Account account = accountService.getAllAccounts().get(accountNumber);
                responseResult = new ResponseResult<Account>("account", Status.Success, account);
                Base.sendAsJson(response, responseResult);
            }
            return;
        }

        double balance = accountService.getAllAccounts().get(accountNumber).getBalance();
        responseResult = new ResponseResult<Double>("balance", Status.Success, balance);
        Base.sendAsJson(response, responseResult);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(request, response);
        response = Base.fixHeaders(response);
    }


}
