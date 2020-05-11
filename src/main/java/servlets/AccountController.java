package servlets;

import base.Base;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.Account;
import services.AccountService;
import services.TransactionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "AccountController", urlPatterns = "/accounts/*")
public class AccountController extends HttpServlet {

    private AccountService accountService;
    private Gson gson;

    public AccountController() {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        accountService = new AccountService();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] path = pathInfo.split("/");

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();

        if (pathInfo == null || pathInfo.equals("/")) {

            Account account = gson.fromJson(payload, Account.class);
            if (accountService.createAccount(account)) {
                sendAsJson(response, account);
                return;
            } else {
                String result = "{'error': 'account already exist'}";
                sendAsJson(response, result);
                return;
            }
        } else if (path[1].equals("depost") && path.length == 2)
        {
            JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);
            String accno = jsonObject.get("accno").getAsString();
            double amount = jsonObject.get("amount").getAsDouble();
            Account account = accountService.FindAccount(accno);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                accountService.updateAccount(account);
                String result = "{'Balance': " + account.getBalance() + "}";
//                transactionService.createTransaction(accno, amount);
                sendAsJson(response, result);
                return;
            } else {
                String result = "{'error': 'The Account Number isn't valid'}";
                sendAsJson(response, result);
                return;
            }


        } else if (path[1].equals("getBalance") && path.length == 2) {

        } else if (path[1].equals("pay") && path.length == 2) {

            HttpSession session = request.getSession(false);
//            if (session != null) {
            JsonObject jobj = gson.fromJson(payload, JsonObject.class);
//                String payer = jobj.get("payer").getAsString();

            String reciver = jobj.get("rcv").getAsString();
            double amount = jobj.get("amount").getAsDouble();
//            Account account = (Account) req.getSession().getAttribute("account");
            //String confirmation = accountService.payMoney("12345", reciver, amount);
//                transactionService.createTransaction(account, +(amount))//
//                transactionService.createTransaction(reciver, -(amount))
            //sendAsJson(response, confirmation);
            return;
        }
//            else {
//                String message =  "{error: you must login first }";
//                sendAsJson(resp, message);
//                return;
//            }


        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){

            Collection<Account> accounts = accountService.getAllAccounts().values();

            sendAsJson(response, accounts);
            return;
        }

        String[] splits = pathInfo.split("/");

        if(splits.length != 2) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String accNo = splits[1];

        if(!accountService.getAllAccounts().containsKey(accNo)) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        sendAsJson(response, accountService.getAllAccounts().get(accNo).getBalance());
        return;
    }

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response = Base.fixHeaders(response);

        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }



    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(request, response);
        response = Base.fixHeaders(response);
    }

}
