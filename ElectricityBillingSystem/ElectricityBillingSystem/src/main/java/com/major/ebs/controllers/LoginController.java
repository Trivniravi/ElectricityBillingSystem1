package com.major.ebs.controllers;

import com.major.ebs.entity.Admin;
import com.major.ebs.entity.Customer;
import com.major.ebs.services.AdminRepository;
import com.major.ebs.services.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class LoginController {
    CustomerRepository repo = new CustomerRepository();
    AdminRepository adminRepository = new AdminRepository();

    @GetMapping("/home")
    public String getHome (Model model){

        String username = adminRepository.getActive();

        ArrayList<Customer> customers = repo.listAll();
        Collections.reverse(customers);
        model.addAttribute("customers",customers);
        model.addAttribute("name",username);
        model.addAttribute("size",customers.size());

        return "home";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model){

        Admin admin = adminRepository.fetchOne(username.toLowerCase().trim());

        if(admin==null){
            model.addAttribute("message","User doesn't exist");
            return "error";
        } else if (!admin.getPassword().equals(password)) {
            model.addAttribute("message","The password you entered was incorrect");
            return "error";
        }
        adminRepository.setActive(admin.getName());

        //System.out.println(repo.listAll());
        return "redirect:/home";
    }

}
