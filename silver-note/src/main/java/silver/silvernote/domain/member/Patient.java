package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Patient extends Member {

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Family> familyList = new ArrayList<>();

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Patient(Center center, String name,  String sex, String rrn, String phone, Address address, JoinStatus status){
        createMember(center, name, sex, rrn, phone, address, status);
    }
}
