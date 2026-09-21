package ozone.com.eproz.repository;
import ozone.com.eproz.enums.GenderEnum;
import ozone.com.eproz.model.StaffModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface StaffRepository extends JpaRepository<StaffModel, UUID> {
    boolean existsByEmail(String email);

    boolean existsByFnameAndLname(String fname, String lname);

    @Query ("SELECT u FROM StaffModel u WHERE " +
            "(:fname IS NULL OR u.fname LIKE %:fname%) AND " +
            "(:lname IS NULL OR u.lname LIKE %:lname%)")
    Page<StaffModel> search(@Param("fname") String fname, @Param("lname") String lname, Pageable pageable);
}
