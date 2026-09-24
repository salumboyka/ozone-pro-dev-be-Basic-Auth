package ozone.com.eproz.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ozone.com.eproz.dto.NextOfKinDtoRequest;
import ozone.com.eproz.dto.NextOfKinDtoResponse;
import ozone.com.eproz.dto.PageResponse;
import ozone.com.eproz.exception.ResourceNotFoundException;
import ozone.com.eproz.model.NextOfKinModel;
import ozone.com.eproz.model.StaffModel;
import ozone.com.eproz.repository.NextOfKinRepository;
import ozone.com.eproz.repository.StaffRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NextOfKinServices {

    private final NextOfKinRepository nextOfKinRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public NextOfKinDtoResponse createNextOfKin(NextOfKinDtoRequest request) {
        StaffModel staff = getStaffOrThrow(request.staffId());

        NextOfKinModel nextOfKin = new NextOfKinModel(
                request.fullName(),
                request.relationship(),
                request.phoneNumber(),
                request.email(),
                request.address(),
                staff
        );

        return NextOfKinDtoResponse.from(nextOfKinRepository.save(nextOfKin));
    }

    public PageResponse<NextOfKinDtoResponse> findAll(UUID staffId, Pageable pageable) {
        Page<NextOfKinModel> page = staffId == null
                ? nextOfKinRepository.findAll(pageable)
                : nextOfKinRepository.findByStaffId(staffId, pageable);

        return PageResponse.from(page.map(NextOfKinDtoResponse::from));
    }

    public NextOfKinDtoResponse findById(UUID id) {
        return NextOfKinDtoResponse.from(getOrThrow(id));
    }

    @Transactional
    public NextOfKinDtoResponse updateNextOfKin(UUID id, NextOfKinDtoRequest request) {
        NextOfKinModel nextOfKin = getOrThrow(id);

        nextOfKin.update(
                request.fullName(),
                request.relationship(),
                request.phoneNumber(),
                request.email(),
                request.address()
        );

        return NextOfKinDtoResponse.from(nextOfKin);
    }

    // @Transactional
    // public void deleteNextOfKin(UUID id) {
    //     NextOfKinModel nextOfKin = getOrThrow(id);
    //     nextOfKinRepository.delete(nextOfKin);
    // }

    private NextOfKinModel getOrThrow(UUID id) {
        return nextOfKinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NextOfKin", id));
    }

    private StaffModel getStaffOrThrow(UUID staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", staffId));
    }
}
