package com.study.board.controller;

import com.study.board.domain.Report;
import com.study.board.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@Slf4j
public class Controller1 {

    @Autowired
    private ReportRepository repository;

    @GetMapping(value = "/write")
    public String write(){
        System.out.println("Controller1.write");
        return "write";
    }

    @PostMapping(value="/confirm")
    public String result(@RequestParam("title") String title,
                         @RequestParam("content")String content,
                         Model model){
        System.out.println("Controller1.result");

        Report report=new Report(title, content);
        System.out.println("title = " + title);
        System.out.println("content = " + content);
        repository.save(report);
        model.addAttribute("report", report);

        return "result";
    }

    @GetMapping(value = "/main")
    public String listAll(Model model){
        System.out.println("Controller1.listAll");
        List<Report> reports = repository.findAll();
        model.addAttribute("reports",reports);

        return "main";
    }

    @GetMapping(value = "/findReportById/{id}")
    public String findReportById(@PathVariable("id") String id,
                                 Model model){
        System.out.println("Controller1.findReportById");

        Optional<Report> byId = repository.findById(Long.valueOf(id).longValue());
        if(byId.isPresent()){
            Report myReport=byId.get();
            model.addAttribute("my_report",myReport);
            return "show_each";
        }else{
            return "error";
        }
    }

    @GetMapping(value="/delete/{id}")
    public String deleteById(@PathVariable("id") String id, Model model){
        System.out.println("Controller1.deleteById");
        Long idd=Long.valueOf(id).longValue();
        repository.deleteById(idd);
        return "redirect:../main";

    }



}
