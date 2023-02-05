package com.example.demo.controller;

import com.example.demo.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
                ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(
            @PathVariable("id") int id
    ) {
        System.out.println(id);
        return "a student";
    }

    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", "30");
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "华中科技大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    // 相应JSON数据（异步请求）
    // Java对象 -> JSON字符串 -> JavaScript对象
    @RequestMapping(path = "/employee")
    @ResponseBody
    public Map<String, Object> getEmployee() {
        Map<String, Object> employee = new HashMap<>();
        employee.put("name", "张三");
        employee.put("age", 30);
        employee.put("salary", 8000.00);
        return employee;
    }

    @RequestMapping(path = "/employees")
    @ResponseBody
    public List<Map<String, Object>> getEmployees() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> employee = new HashMap<>();
        employee.put("name", "张三");
        employee.put("age", 23);
        employee.put("salary", 8000.00);
        list.add(employee);

        employee = new HashMap<>();
        employee.put("name", "李四");
        employee.put("age", 24);
        employee.put("salary", 9000.00);
        list.add(employee);

        employee = new HashMap<>();
        employee.put("name", "王五");
        employee.put("age", 25);
        employee.put("salary", 10000.00);
        list.add(employee);

        return list;
    }
}
