package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter  // Setter 는 가급적 안하는 게 좋다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String zipcode;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
