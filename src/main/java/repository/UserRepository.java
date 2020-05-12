package repository;

import models.User;

import java.io.Serializable;
import java.util.HashMap;

public class UserRepository implements Serializable {

    // private User user;
    private HashMap<String, User> users;

    public UserRepository(){
        users = new HashMap<>();
        CreateUsers();
    }

    private void CreateUsers() {
        users.put("omar",new User("omar","333","abc123"));
        users.put("ahmad",new User("ahmad","111","a1234"));
    }

    //get account number for rendering page
    public String getAccountNUmber(String username) {
        return getUserByUsername(username).getAccountNumber();
    }

    public void addUser(User user) {
        users.put(user.getUserName(),user);
    }

    public User getUserByUsername(String username){
        return users.get(username);
    }

    public void deleteUser(String username){
        users.remove(username);
    }

    public HashMap<String,User> getUsers(){
        return users;
    }

}