package ao.it.chandsoft.world.controller;

import ao.it.chandsoft.world.domain.UserModel;
import ao.it.chandsoft.world.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> findAllUsers() {
        return userService.findAllUsers();
    }
}
