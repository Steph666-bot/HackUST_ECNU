package com.rfli.springbootrf.controller;

import com.rfli.springbootrf.entity.DocEntity;
import com.rfli.springbootrf.service.DocService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    private final DocService service;

    public AdminController(DocService service) {
        this.service = service;
    }

    /**
     * 返回所有文件的所有数据
     *
     * @return
     */
    @GetMapping("admin/getAllDoc")
    @CrossOrigin
    public List<DocEntity> getAllDoc() {
        return this.service.listAll();
    }
}
