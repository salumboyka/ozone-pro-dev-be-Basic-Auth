package ozone.com.eproz.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ozone.com.eproz.model.TrainingModel;

public interface TrainingRepository extends JpaRepository<TrainingModel, UUID> {

    Page<TrainingModel> findByStaffId(UUID staffId, Pageable pageable);
}