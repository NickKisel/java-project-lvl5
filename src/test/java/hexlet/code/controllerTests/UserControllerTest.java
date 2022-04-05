package hexlet.code.controllerTests;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigTest;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static hexlet.code.config.SpringConfigTest.TEST_PROFILE;
import static hexlet.code.controller.UserController.ID;
import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static hexlet.code.utils.TestUtils.fromJsom;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigTest.class)
@Transactional
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtils testUtils;

//    @AfterEach
//    public void clear() {
//        testUtils.tearDown();
//    }

    @Test
    public void registrationTest() throws Exception {
        assertThat(userRepository.count()).isEqualTo(0);
        testUtils.regDefaultUser();
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void getUserTest() throws Exception {
        testUtils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        final MockHttpServletResponse response = testUtils.perform(
                        get(USER_CONTROLLER_PATH + ID, expectedUser.getId()),
                        expectedUser.getEmail()
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final User user = fromJsom(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(user.getId()).isEqualTo(expectedUser.getId());
        assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(user.getFirstName()).isEqualTo(expectedUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(expectedUser.getLastName());
    }

    @Test
    public void getUserByIdFails() throws Exception {
        testUtils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        testUtils.perform(get(USER_CONTROLLER_PATH + ID, expectedUser.getId()), "asd@asd")
                .andExpect(status().isUnauthorized());
    }
}
