package ozone.com.eproz.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ozone.com.eproz.model.NextOfKinModel;

public interface NextOfKinRepository extends JpaRepository<NextOfKinModel, UUID> {

    Page<NextOfKinModel> findByStaffId(UUID staffId, Pageable pageable);

    boolean existsByPhoneNumberAndStaffId(String phoneNumber, UUID staffId);
}
