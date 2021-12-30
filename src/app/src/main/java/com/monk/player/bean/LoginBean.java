package com.monk.player.bean;

/**
 * Created by andy on 2017/12/31.
 */

public class LoginBean {

    /**
     * user : {"id":4,"name":"18819259421","password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"}
     * token : eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0In0.HONoeupIphv1jzfUSRdZOK5VUxoDDtIFITKwGAuLXcM
     */

    private UserBean user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String token;
    private String message;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * id : 4
         * name : 18819259421
         * password : 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
         */

        private int id;
        private String name;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
