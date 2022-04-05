package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    public static final String TEST_USERNAME = "nick@nick.com";
    public static final String TEST_USERNAME2 = "mail@mail.com";

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private final UserDto testRegistrationDto = new UserDto(
            TEST_USERNAME,
            "Nick",
            "Nick",
            "password"
    );

    public UserDto getTestRegistrationDto() {
        return testRegistrationDto;
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHelper jwtHelper;

    public void tearDown() {
        userRepository.deleteAll();
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).get();
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testRegistrationDto);
    }

    public ResultActions regUser(final UserDto userDto) throws Exception {
        final MockHttpServletRequestBuilder request = post(USER_CONTROLLER_PATH)
                .content(toJson(userDto))
                .contentType(MediaType.APPLICATION_JSON);
        return perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request, final String username) throws Exception {
        final String token = jwtHelper.expiring(Map.of("username", username));
        request.header(AUTHORIZATION, token);
        return perform(request);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJsom(final String json, final TypeReference<T> type) throws JsonProcessingException {
        return MAPPER.readValue(json, type);
    }
}
