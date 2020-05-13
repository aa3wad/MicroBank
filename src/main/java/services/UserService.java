package services;


import models.User;
import repository.UserRepository;

public class UserService {

    private UserRepository userRepository;
    private AccountService accountService;

    public UserService() {
        userRepository = new UserRepository();
        accountService = new AccountService();
    }
    public boolean AddUser(User user){
        if(!CheckUserExistence(user.getUserName())){
            userRepository.addUser(user);
            return true;
        }
        return false;
    }

    private boolean CheckUserExistence(String username) {
        return userRepository.getUsers().containsKey(username);
    }

    public String getAccountNumber(String username){
        return accountService.findAccountByUser(username) == null? "" : accountService.findAccountByUser(username).getAccountNumber() ;
    }

    public Boolean validateUser(String username, String password) {
        return (userRepository.getUsers().containsKey(username)
                && userRepository.getUsers().get(username).getPassword().equals(password));
    }

    public String getAll(){
        return  userRepository.getUsers().toString();
    }


}

