package controllers;

import dto.UserDto;
import interfaces.UserCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@Validated
public class UserController {
    @Autowired
    private UserCrudService userService;

    @GetMapping("/users")
    public List<UserDto> getAll() {
        log.info("users");
        List<UserDto> users = userService.getAll();
        log.info(users.toString());
        return users;
    }

    @GetMapping("/users/{usersId}")
    public UserDto getById(@PathVariable @Min(1) int usersId) {
        log.info("getEmployee, usersId = {}", usersId);
        UserDto user = userService.getById(usersId);
        log.info(user.toString());
        return user;
    }

    @PutMapping("/users/{userId}")
    public UserDto update(
            @Valid @RequestBody UserDto userDto, @PathVariable @Min(1) int userId) {
        userDto.setUserId(userId);
        log.info("updateEmployee, userDto: {}", userDto);
        UserDto user = userService.update(userDto);
        log.info(user.toString());
        return user;
    }

    @PostMapping("/users")
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("createEmployee, userDto: {}", userDto);
        UserDto user = userService.create(userDto);
        log.info(user.toString());
        return user;
    }

    @DeleteMapping("/users/{usersId}")
    public void delete(@PathVariable @Min(1) int usersId) {
        log.info("deleteEmployee, usersId = {}", usersId);
        userService.delete(userService.getById(usersId));
        log.info("deleted user, usersId = {}", usersId);
    }
}
