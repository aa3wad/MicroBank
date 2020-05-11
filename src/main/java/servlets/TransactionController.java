package servlets;

import base.Base;
import base.ResponseResult;
import base.Status;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Account;
import models.AccountEntry;
import services.AccountService;
import services.TransactionService;
import services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "TransactionController", urlPatterns = "/transaction/*")
public class TransactionController extends HttpServlet {

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
        String transaction = path[1];
        String requestBody = Base.ReadRequestBody(request);
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);

        if (transaction.equals("withdraw") || (transaction.equals("deposit")) && path.length == 2) {

            String accountNo = jsonObject.get("accountNumber").getAsString();
            double amount = jsonObject.get("amount").getAsDouble();

            if(transaction.equals("withdraw")) {
                accountService.withdraw(accountNo, amount);
            }
            else if(transaction.equals("deposit")){
                accountService.deposit(accountNo, amount);
            }

            double accountBalance = accountService.getAccountBalance(accountNo);
            responseResult = new ResponseResult<Double>("Balance", Status.Success, accountBalance);
            Base.sendAsJson(response, responseResult);
        }
        else if (transaction.equals("transferFunds") && path.length == 2)
        {
            String fromAccountNo = jsonObject.get("fromAccountNo").getAsString();
            String toAccountNo = jsonObject.get("toAccountNo").getAsString();
            double amount = jsonObject.get("amount").getAsDouble();

            String msg = accountService.transferFunds(fromAccountNo, toAccountNo, amount);
            responseResult = new ResponseResult<String>(msg, Status.Success, msg);
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

        //in case pathInfo == "/" then return all transactions
        if(pathInfo.equals("/")){

             Stream<Account> stream = accountService.getAllAccounts().values().stream();
            List<AccountEntry> accountEntries = stream.flatMap(account -> account.getAccountEntries().stream())
                    .collect(Collectors.toList());

            responseResult = new ResponseResult<List<AccountEntry>>("all transactions", Status.Success, accountEntries);
            Base.sendAsJson(response, responseResult);
            return;
        }
        else if(path.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //In case path[1] has value then return specific account's transactions
        String accountNumber = path[1];
        if(!accountNumber.isEmpty() && accountNumber != null) {
            if (!accountService.getAllAccounts().containsKey(accountNumber)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else {
                Collection<AccountEntry> accountEntries = accountService
                        .getAllAccounts()
                        .get(accountNumber)
                        .getAccountEntries();
                responseResult = new ResponseResult<Collection<AccountEntry>>("all account's transactions", Status.Success, accountEntries);
                Base.sendAsJson(response, responseResult);
            }
            return;
        }

        double balance = accountService.getAllAccounts().get(accountNumber).getBalance();
        responseResult = new ResponseResult<Double>("", Status.Success, balance);
        Base.sendAsJson(response, responseResult);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(request, response);
        response = Base.fixHeaders(response);
    }

}
