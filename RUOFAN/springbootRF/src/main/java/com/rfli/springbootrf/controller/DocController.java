package com.rfli.springbootrf.controller;

import com.rfli.springbootrf.entity.DocEntity;
import com.rfli.springbootrf.service.DocService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class DocController {
    private final DocService docService;

    public DocController(DocService docService) {
        this.docService = docService;
    }

    /**
     * 文件管理界面
     */
    @GetMapping("/doc/manage")
    public ModelAndView docManage() {
        List<DocEntity> docs = this.docService.listAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("docManage");
        mv.addObject("docs", docs);
        return mv;
    }

    /**
     * 添加文件信息
     */
    @PostMapping("/doc/addByForm")
    public ModelAndView addDocForm(@RequestParam String doc_identifier, @RequestParam int count) {
        DocEntity docEntity = new DocEntity();
        docEntity.setDoc_identifier(doc_identifier);
        docEntity.setCount(count);
        this.docService.saveAndInit(docEntity);
        return new ModelAndView("redirect:/doc/manage");
    }

    /**
     * 修改文件信息
     */
    @PostMapping("/doc/updateByForm")
    public ModelAndView updateDocForm(@RequestParam int id, @RequestParam String doc_identifier,
                                      @RequestParam int count) {
        DocEntity docEntity = new DocEntity();
        docEntity.setId(id);
        docEntity.setDoc_identifier(doc_identifier);
        docEntity.setCount(count);
        this.docService.updateDoc(docEntity);
        return new ModelAndView("redirect:/doc/manage");
    }

    /**
     * 删除文件信息
     */
    @PostMapping("/doc/delByForm")
    public ModelAndView delDocForm(@RequestParam int id) {
        this.docService.deleteDocById(id);
        return new ModelAndView("redirect:/doc/manage");
    }

    /**
     * 添加文件信息
     */
    @PostMapping("/doc/add")
    public int addDoc(@RequestBody DocEntity d) {
        return this.docService.save(d);
    }

    /**
     * 添加文件信息
     */
    @PostMapping("/doc/add1")
    public int addDoc1(@RequestBody DocEntity d) {
        return this.docService.saveAndInit(d);
    }

    /**
     * 更新文件信息
     */
    @PostMapping("/doc/update")
    public int updateDoc(@RequestBody DocEntity d) {
        return this.docService.updateDoc(d);
    }

    /**
     * 根据文件id删除文件
     */
    @PostMapping("/doc/del")
    public int deleteDocById(@RequestParam int id) {
        return this.docService.deleteDocById(id);
    }

    /**
     * 根据文件id查询文件
     */
    @GetMapping("/doc/get")
    public DocEntity getDocById(@RequestParam int id) {
        return this.docService.getDocById(id);
    }

    /**
     * 查询所有文件信息
     */
    @GetMapping("/doc/getAll")
    public List<DocEntity> listAll() {
        return this.docService.listAll();
    }


}
