package ozone.com.eproz.conroler;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.NextOfKinDtoRequest;
import ozone.com.eproz.dto.NextOfKinDtoResponse;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.service.NextOfKinServices;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/next-of-kin")
public class NextOfKinController {

    private final NextOfKinServices nextOfKinServices;

    @PostMapping
    public ResponseEntity<NextOfKinDtoResponse> createNextOfKin(
            @Valid @RequestBody NextOfKinDtoRequest request,
            UriComponentsBuilder url) {
        NextOfKinDtoResponse created = nextOfKinServices.createNextOfKin(request);
        URI location = url.path("/api/v1/next-of-kin/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public PageResponse<NextOfKinDtoResponse> findAll(
            @RequestParam(required = false) UUID staffId,
            @RequestParam(defaultValue = "0") int page,
            @PageableDefault(size = 10) Pageable pageable) {
        return nextOfKinServices.findAll(staffId, pageable);
    }

    @GetMapping("/{id}")
    public NextOfKinDtoResponse findById(@PathVariable UUID id) {
        return nextOfKinServices.findById(id);
    }

    @PutMapping("/{id}")
    public NextOfKinDtoResponse updateNextOfKin(@PathVariable UUID id,
                                              @Valid @RequestBody NextOfKinDtoRequest request) {
        return nextOfKinServices.updateNextOfKin(id, request);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteNextOfKin(@PathVariable UUID id) {
    //     nextOfKinServices.deleteNextOfKin(id);
    //     return ResponseEntity.noContent().build();
    // }
}
