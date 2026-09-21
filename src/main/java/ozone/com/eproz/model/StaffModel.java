package ozone.com.eproz.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import ozone.com.eproz.enums.GenderEnum;

@Entity 


@Table(name = "staff", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint (columnNames = "username")
})

@Getter

@Setter

@NoArgsConstructor(access = AccessLevel.PROTECTED)  
@ToString (of = {"id", "email"})

public class StaffModel extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "fname", nullable = false, length = 120)
    private String fname;

    @Column(name = "mname", nullable = false, length = 120)
    private String mname;

    @Column(name = "lname", nullable = false, length = 120)
    private String lname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 120)
    private GenderEnum gender = GenderEnum.MALE;

    @Column(name = "age", nullable = false, length = 120)
    private int age;

    @Column(name = "address", nullable = false, length = 120)
    private String address;

    public StaffModel(String email, String fname, String mname, String lname, GenderEnum gender, int age, String address) {
        this.email = email;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.gender = gender;
        this.age = age;
        this.address = address; 
    }

    public void update(String email, int age, String mname, String lname, GenderEnum gender, String fname, String address) {
        this.email = email;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.gender = gender;
        this.age = age;
        this.address = address; 
    }


}
