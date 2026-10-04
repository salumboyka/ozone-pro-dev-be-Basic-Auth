package ozone.com.eproz.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.dto.UserDtoRequest;
import ozone.com.eproz.dto.UserDtoResponse;
import ozone.com.eproz.exception.DuplicateResourceException;
import ozone.com.eproz.exception.ResourceNotFoundException;
import ozone.com.eproz.model.RoleModel;
import ozone.com.eproz.model.UserModel;
import ozone.com.eproz.model.UserRoleModel;
import ozone.com.eproz.repository.RoleRepository;
import ozone.com.eproz.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServices {

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder passwordEncoder;

    public PageResponse<UserDtoResponse> findAll(Pageable pageable) {
        return PageResponse.from(users.findAll(pageable).map(UserDtoResponse::from));
    }

    @Transactional
    public UserDtoResponse register(UserDtoRequest request) {
        if (users.existsByUsernameOrEmail(request.username(), request.email())) {
            throw new DuplicateResourceException("Username or email already exists");
        }

        RoleModel role = roles.findById(request.role())
            .orElseThrow(() -> new ResourceNotFoundException("Role", request.role()));

        UserModel user = new UserModel(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        user.getUserRoles().add(new UserRoleModel(user, role));

        return UserDtoResponse.from(users.save(user));
    }
}