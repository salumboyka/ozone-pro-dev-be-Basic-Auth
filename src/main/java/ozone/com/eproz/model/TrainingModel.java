package ozone.com.eproz.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "training")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "title"})
public class TrainingModel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "provider", nullable = false, length = 150)
    private String provider;

    @Column(name = "attachment", length = 255)
    private String attachment;

    @Transient
    private MultipartFile attachmentFile;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffModel staff;

    public TrainingModel(String title, String description, String provider, String attachment,
                         LocalDate startDate, LocalDate endDate, StaffModel staff) {
        this.title = title;
        this.description = description;
        this.provider = provider;
        this.attachment = attachment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.staff = staff;
    }

    public void update(String title, String description, String provider, String attachment,
                       LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.provider = provider;
        this.attachment = attachment;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}