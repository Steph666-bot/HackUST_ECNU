package com.rfli.springbootrf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.rfli.springbootrf.Util.Result;
import com.rfli.springbootrf.Util.Schnorr;
import com.rfli.springbootrf.entity.UserEntity;
import com.rfli.springbootrf.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
public class UserRegisterController {
    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User register interface.
     *
     * @param userEntity includes username and password
     * @return 0：register success
     * <br/>1：username has been registered
     */
    @CrossOrigin
    @PostMapping("/register/action")
    public Result userRegister(@RequestBody UserEntity userEntity) throws IOException {

        //read all user data from local file

        JSONObject users = JSONObject.parseObject(new String(new FileInputStream("src/main/java/com/rfli/springbootrf/data/user/users.json").readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

        //judge whether the username has been registered
        if (users != null) {
            if (users.get(userEntity.getUsername()) != null) {
                return Result.fail("The target user is already existed in the database!", 1);
            }
        }

        UserEntity user = new UserEntity(userEntity.getUsername(), userEntity.getPassword());

        //generate public key and secret key of the signature for the user
        String keys = Schnorr.generateKey();

        int index = keys.indexOf("/");
        //Extract the public key and secret key pair
        String pk = keys.substring(0, index);
        String sk = keys.substring(index + 1);
        user.setPk(pk);
        user.setSk(sk);

        //save the new registered user's data
        JSONObject userValue = new JSONObject();
        userValue.put("password", user.getPassword());
        userValue.put("pk", user.getPk());
        userValue.put("sk", user.getSk());

        users.put(user.getUsername(), userValue);

        File userFile = new File("src/main/java/com/rfli/springbootrf/data/user/users.json");

        FileWriter fileWriter = new FileWriter(userFile.getAbsoluteFile(), false);
        BufferedWriter bw = null;
        bw = new BufferedWriter(fileWriter);
        bw.write(users.toString());//转化成字符串再写
        bw.close();

        return Result.succ(0);
    }


}
