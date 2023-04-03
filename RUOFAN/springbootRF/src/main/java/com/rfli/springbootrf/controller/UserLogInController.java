package com.rfli.springbootrf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.rfli.springbootrf.Util.Result;
import com.rfli.springbootrf.entity.UserEntity;
import com.rfli.springbootrf.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class UserLogInController {
    private final UserService userService;

    public UserLogInController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User login interface.
     *
     * @param userEntity includes username and password
     * @return -1：admin login success
     * <br/>0：client login success
     * <br/>1：account not exist
     * <br/>2：wrong password
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result userLogin(@RequestBody UserEntity userEntity) throws IOException {

        //read all user data from local file
        JSONObject users = JSONObject.parseObject(new String(new FileInputStream("src/main/java/com/rfli/springbootrf/data/user/users.json").readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

        //judge whether the account exists
        if (users.get(userEntity.getUsername()) == null) {
            return Result.fail("The target user does not exist in the database!", 1);
        }
        JSONObject user = (JSONObject) users.get(userEntity.getUsername());

        //judge whether the password is correct
        if (!user.get("password").toString().equals(userEntity.getPassword().toString())) {
            return Result.fail("Password is wrong!", 2);
        }

        if (userEntity.getUsername().equals("admin")) {
            return Result.succ(-1);
        }

        return Result.succ(0);

    }
}
