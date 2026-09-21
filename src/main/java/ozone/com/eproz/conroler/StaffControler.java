package ozone.com.eproz.conroler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.dto.StaffDtoRequest;
import ozone.com.eproz.dto.StaffDtoResponse;
import ozone.com.eproz.service.StaffServices;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")

public class StaffControler {

    private final StaffServices userServices;

    @PostMapping
    public ResponseEntity<StaffDtoResponse> createUser(
            @Valid @RequestBody StaffDtoRequest request,
            UriComponentsBuilder url) {
        StaffDtoResponse createdUser = userServices.createStaff(request);
        URI location = url.path("/api/v1/users/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping
    public PageResponse<StaffDtoResponse> findAll(
            @RequestParam(required = false) String fname,
            @RequestParam(required = false) String lname,
            @RequestParam(defaultValue = "0") int page,
            @PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable) {
        return userServices.findAll(fname, lname, pageable);
    }

    @GetMapping("/{id}")
    public StaffDtoResponse findById(@PathVariable UUID id) {
        return userServices.findById(id);
    }

    @PutMapping ("/{id}")
    public StaffDtoResponse updateStaff(@PathVariable UUID id, @Valid @RequestBody StaffDtoRequest u) {
        return userServices.updateStaff(id, u);
    }

}
