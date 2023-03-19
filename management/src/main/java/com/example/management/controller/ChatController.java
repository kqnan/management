package com.example.management.controller;


import com.example.management.service.ChatService;
import com.example.management.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PreAuthorize("hasAnyAuthority('销售部','营销部','服务部')")
    @PostMapping("/select")
    private CommonResult selectAll(){
        return chatService.selectAll();
    }
}

