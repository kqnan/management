package com.example.management.controller;


import com.example.management.pojo.Customer;
import com.example.management.service.CustomerService;
import com.example.management.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAnyAuthority('销售部','营销部','服务部')")
    @PostMapping("/select")
    public CommonResult selectAll(){
        return customerService.selectAll();
    }

    @PreAuthorize("hasAuthority('销售部')")
    @PostMapping("/add")
    public CommonResult addCustomer(Customer customer){
        return customerService.addCustomer(customer);
    }

    @PreAuthorize("hasAuthority('销售部')")
    @PostMapping("/delete")
    public CommonResult deleteCustomer(int customerId) {
        return customerService.deleteCustomer(customerId);
    }

    @PreAuthorize("hasAnyAuthority('销售部','营销部','服务部')")
    @PostMapping("/select/id")
    public CommonResult selectById(int customerId){
        return customerService.selectById(customerId);
    }
}

