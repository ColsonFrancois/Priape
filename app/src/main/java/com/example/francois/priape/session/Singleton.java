package com.example.francois.priape.session;

import com.example.francois.priape.Model.User;

/**
 * Created by Francois on 18/05/2016.
 */
public class Singleton {
    private static Singleton instance = null;
    private static User user;

    protected Singleton(){}

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

  public void setUser(User myUser){
      if(user == null)
      {
          user = new User();
      }
    user = myUser;
  }
    public User getUser()
    {
        return user;
    }

}
