package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("F")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Family extends Member{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Family(Patient patient, Center center,  String loginId, String password, String name, String email, String sex, String rrn, String phone, Address address, JoinStatus status){
        createMember(center, loginId, password, name, email, sex, rrn, phone, address, status);
        this.patient = patient;
    }
}
