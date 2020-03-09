package com.example;

import com.example.dao.Message;
import com.example.dataJPA.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(String name, Model model) {
        return "greeting";
    }

    @GetMapping("/mainPage")
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "mainPage";
    }

    @PostMapping("/mainPage")
    public String add(@RequestParam String text, @RequestParam String tag, Map <String, Object> model){
        Message message = new Message(text, tag);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "mainPage";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map <String, Object> model){
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        }else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "mainPage";
    }

}