package silver.silvernote.domain.center;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center {

    @Id
    @GeneratedValue
    @Column(name = "center_id")
    private Long id;
    @NotBlank
    private String name;

    private String phone;
    private String description;

    @NotNull
    private String address;

    @NotNull
    private String zipcode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;



    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Center(String name, String phone, String description, String address, String zipcode, PaymentStatus status) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.address = address;
        this.zipcode = zipcode;
        this.status = status;
    }

    public void updateData(String name, String phone, String description, String address, String zipcode){
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.address = address;
        this.zipcode = zipcode;
    }

    public void updateStatus(PaymentStatus status){ this.status = status; }
}
