package com.kayleh.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    //显示评价者的时候进行匿名显示
    public String getAnonymousName(){
        if(null==name)
            return null;

        if(name.length()<=1)
            return "*";

        if(name.length()==2)
            return name.substring(0,1) +"*";

        //当长度大于等于2的情况
        char[] chars =name.toCharArray();
        //除了开头和结尾 其他都是*
        for (int i = 1; i < chars.length-1; i++) {
            chars[i]='*';
        }
        return new String(chars);

    }
}