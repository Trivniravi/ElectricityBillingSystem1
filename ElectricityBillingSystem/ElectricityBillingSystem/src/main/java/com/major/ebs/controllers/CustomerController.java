package com.major.ebs.controllers;

import com.major.ebs.entity.Customer;
import com.major.ebs.services.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @RequestMapping("/new")
    public String newCustomer(Model model){
        return "new";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id){
        CustomerRepository repository = new CustomerRepository();
        repository.delete(id);
        return "redirect:/home";
    }

    @PostMapping("/pay")
    public String processPayment(@RequestParam("units") double units, @RequestParam("id") int id, Model model) {
        CustomerRepository repository = new CustomerRepository();
        repository.rechargeUnits(id, units);
        return "redirect:/view?id="+id;
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam("id") int id, Model model){
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.findById(id);
        model.addAttribute("customer", customer);
        return "edit";
    }

    @PostMapping("/submitUpdate")
    public String updateCustomer(Customer customer, Model model){

        CustomerRepository repository = new CustomerRepository();

        if(customer == null ){
            model.addAttribute("error_message","Customer values not received");
            return "new";
        }
        if(!customer.isComplete()){
            model.addAttribute("error_message","Enter all fields");
            return "new";
        }

        repository.update(customer);
        model.addAttribute("success_message","Added "+customer.getName()+" successfully");

        return "redirect:/view?id="+customer.getId();
    }

    @PostMapping("/submitNew")
    public String addCustomer(Customer customer, Model model) {

        CustomerRepository repository = new CustomerRepository();
        if(customer == null ){
            model.addAttribute("error_message","Customer values not received");
            return "new";
        }
        if(!customer.isComplete()){
            model.addAttribute("error_message","Enter all fields");
            return "new";
        }

        customer.setUnits(0.0);
        repository.save(customer);
        model.addAttribute("success_message","Added "+customer.getName()+" successfully");
        return "new";
    }

    @GetMapping("/view")
    public String view(@RequestParam("id") int id , Model model){
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.findById(id);
        model.addAttribute("customer", customer);
        return "view";
    }
}
