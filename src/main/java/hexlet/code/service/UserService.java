package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;


public interface UserService {
    User createUser(UserDto userDto);
    User updateUser(long id, UserDto userDto);
    void deleteUser(long id);
    String getCurrentUserName();
    User getCurrentUser();
}
