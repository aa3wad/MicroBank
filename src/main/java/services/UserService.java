package services;


import models.User;
import repository.UserRepository;

public class UserService {

    private UserRepository userRepository;
    private AccountService accountDaoService;

    public UserService() {
        userRepository = new UserRepository();
        accountDaoService = new AccountService();
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
        return userRepository.getAccountNUmber(username);
    }

    public Boolean validateUser(String username, String password) {
        return (userRepository.getUsers().containsKey(username)
                && userRepository.getUsers().get(username).getPassword().equals(password));
    }

    public String getAll(){
        return  userRepository.getUsers().toString();
    }


}

