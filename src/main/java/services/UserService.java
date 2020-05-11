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


}

