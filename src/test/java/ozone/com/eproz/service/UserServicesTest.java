package ozone.com.eproz.service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import ozone.com.eproz.dto.UserDtoRequest;
import ozone.com.eproz.dto.UserDtoResponse;
import ozone.com.eproz.repository.RoleRepository;
import ozone.com.eproz.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServicesTest {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerHashesPasswordAndAssignsRequestedRole() {
        String username = "test-" + UUID.randomUUID();
        String email = username + "@example.com";
        String password = "correct-horse-battery";
        var role = roleRepository.findAll().get(0);
        UserDtoRequest request = new UserDtoRequest(username, email, password, role.getId());

        assertThat(request.toString()).doesNotContain(password);

        UserDtoResponse response = userServices.register(request);

        assertThat(response.roles()).containsExactly(role.getName());

        var persistedUser = userRepository.findById(response.id()).orElseThrow();
        assertThat(persistedUser.getPasswordHash()).isNotEqualTo(password);
        assertThat(passwordEncoder.matches(password, persistedUser.getPasswordHash())).isTrue();
    }
}