package com.timeOrganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/todoList")
    public String getTodoList(){
        return "views/todoList";
    }
    @GetMapping("/viewHistory")
    public String getViewHistory(){
        return "views/viewHistory";
    }
}
