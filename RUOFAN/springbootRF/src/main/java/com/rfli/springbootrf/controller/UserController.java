//package com.rfli.springbootrf.controller;
//
//import com.rfli.springbootrf.entity.UserEntity;
//import com.rfli.springbootrf.service.UserService;
//import org.apache.catalina.User;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * 用户管理界面
//     */
//    @GetMapping("/user/manage")
//    public ModelAndView userManage() {
//        List<UserEntity> users = this.userService.listAll();
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("userManage");
//        mv.addObject("users", users);
//        return mv;
//    }
//
////    @PostMapping("/test")
////    public String login(@RequestBody UserEntity user) {
////
////        System.out.println(user.getUsername());
////
////        return user.getUsername();
////    }
//
//    /**
//     * 添加用户信息
//     */
//    @PostMapping("/user/addByForm")
//    public ModelAndView addUserForm(@RequestParam String username, @RequestParam String password) {
//        UserEntity user = new UserEntity();
//        user.setUsername(username);
//        user.setPassword(password);
//        this.userService.save(user);
//        return new ModelAndView("redirect:/user/manage");
//    }
//
//    /**
//     * 修改用户信息
//     */
//    @PostMapping("/user/updateByForm")
//    public ModelAndView updateUserForm(@RequestParam int id, @RequestParam String username,
//                                       @RequestParam String password) {
//        UserEntity user = new UserEntity();
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        this.userService.updateUser(user);
//        return new ModelAndView("redirect:/user/manage");
//    }
//
//    /**
//     * 删除用户信息
//     */
//    @PostMapping("/user/delByForm")
//    public ModelAndView delUserForm(@RequestParam int id) {
//        this.userService.deleteUserById(id);
//        return new ModelAndView("redirect:/user/manage");
//    }
//
//    /**
//     * 添加用户信息
//     */
//    @PostMapping("/user/add")
//    public int addUser(@RequestBody UserEntity u) {
//        return this.userService.save(u);
//    }
//
//    /**
//     * 更新用户信息
//     */
//    @PostMapping("/user/update")
//    public int updateUser(@RequestBody UserEntity u) {
//        return this.userService.updateUser(u);
//    }
//
//    /**
//     * 根据用户id删除用户
//     */
//    @PostMapping("/user/del")
//    public int deleteUserById(@RequestParam int id) {
//        return this.userService.deleteUserById(id);
//    }
//
//    /**
//     * 根据用户id查询用户
//     */
//    @GetMapping("/user/get")
//    public UserEntity getUserById(@RequestParam int id) {
//        return this.userService.getUserById(id);
//    }
//
//    /**
//     * 查询所有用户信息
//     */
//    @GetMapping("/user/getAll")
//    public List<UserEntity> listAll() {
//        return this.userService.listAll();
//    }
//}