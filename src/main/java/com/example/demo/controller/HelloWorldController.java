package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/")
    public List json(HttpServletRequest request) {
        System.out.println("===json===");
        System.out.println("getServerName====="+request.getServerName());
        System.out.println("getServerPort====="+request.getServerPort());
        List<Human> list = new ArrayList<>();
        list.add(new Human("死地方", 223));
        list.add(new Human("反得分", 21));
        return list;
    }

}

class Human{
    private String name;
    private int age;

    public Human(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
