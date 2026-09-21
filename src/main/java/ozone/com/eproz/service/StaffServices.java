package ozone.com.eproz.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.dto.StaffDtoRequest;
import ozone.com.eproz.dto.StaffDtoResponse;
import ozone.com.eproz.exception.ResourceNotFoundException;
import ozone.com.eproz.model.StaffModel;
import ozone.com.eproz.repository.StaffRepository;

@Service 
@RequiredArgsConstructor

@Transactional (readOnly = true)

public class StaffServices {

    private final StaffRepository users;

    @Transactional
    public StaffDtoResponse createStaff(StaffDtoRequest userDtoRequest) {
        // Implement the logic to create a user
        StaffModel staffModel = new StaffModel(
            userDtoRequest.email(),
            userDtoRequest.fname(),
            userDtoRequest.mname(),
            userDtoRequest.lname(),
            userDtoRequest.gender(),
            userDtoRequest.age(),
            userDtoRequest.address()
        );
        return StaffDtoResponse.from(users.save(staffModel));
    }
    

    public PageResponse<StaffDtoResponse> findAll(String fname, String lname, Pageable pageable) {
        
        return PageResponse.from(users.search(fname, lname, pageable).map(StaffDtoResponse::from));

    }

    public StaffDtoResponse findById(UUID id) {
        return StaffDtoResponse.from(getOrThrow(id));
    }

    @Transactional 
    public StaffDtoResponse updateStaff(UUID id, StaffDtoRequest u) {
        StaffModel staffModel = getOrThrow(id);
        
        staffModel.update(u.address(), u.age(), u.email(), u.fname(), u.gender(), u.lname(), u.mname());
        return StaffDtoResponse.from(staffModel);
    }


    private StaffModel getOrThrow(UUID id) {
        return users.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    

}
