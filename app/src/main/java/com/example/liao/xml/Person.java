package com.example.liao.xml;

public class Person {
    private int id;
    private String age;
    private String name;

    public Person(int id, String age, String name) {
        this.id = id;
        this.name = name;
        this.age = age;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "姓名" + name + "年龄" + age;
    }
}
