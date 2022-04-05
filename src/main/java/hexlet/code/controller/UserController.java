package hexlet.code.controller;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;

@RestController
@RequestMapping("${base-url}" + USER_CONTROLLER_PATH)
@AllArgsConstructor
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";
    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;

    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = ID)
    public User getUser(@PathVariable long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No users with such id"));
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new DuplicateKeyException("User with this email is already exist");
        }
        return userService.createUser(userDto);
    }

    @PutMapping(ID)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public User updateUser(
            @PathVariable long id,
            @RequestBody @Valid UserDto userDto
    ) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping(ID)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void deleteUser(@PathVariable long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No users with such id"));
        userService.deleteUser(id);
    }
}
