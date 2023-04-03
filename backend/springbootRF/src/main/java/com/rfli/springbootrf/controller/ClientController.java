package com.rfli.springbootrf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.rfli.springbootrf.Util.Result;
import com.rfli.springbootrf.entity.DocEntity;
import com.rfli.springbootrf.service.DocService;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ClientController {
    private final DocService service;

    public ClientController(DocService service) {
        this.service = service;
    }

    /**
     * User gets all doc identifiers
     *
     * @return a String list, which includes all doc identifiers
     */
    @GetMapping("/client/getAllDoc")
    @CrossOrigin
    public Result getAllDoc() throws IOException {

        //read all doc data from files
        JSONObject docs = JSONObject.parseObject(new String(new FileInputStream("src/main/java/com/rfli/springbootrf/data/doc.json").readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

        int count = docs.size();
        System.out.println();
        List options = new ArrayList<DocEntity>();

        //add all doc data into a single list
        for (int i = 1; i <= count; i++) {
            JSONObject jsonObject = (JSONObject) docs.get(String.valueOf(i));
            DocEntity docEntity = new DocEntity();
            docEntity.setId((Integer) jsonObject.get("id"));
            docEntity.setDoc_identifier((String) jsonObject.get("doc_identifier"));
            docEntity.setCount((Integer) jsonObject.get("count"));
            options.add(docEntity);
        }

        return Result.succ(options);
    }

}
