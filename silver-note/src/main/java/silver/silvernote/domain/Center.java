package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Address address;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Center(String name, String phone, String description, Address address) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.address = address;
    }

    public void updateData(String name, String phone, String description, Address address){
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.address = address;
    }
}
