package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Employee extends Member {

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Employee(Center center,  String loginId, String password, String name, String email, String sex, String rrn, String phone, Address address, JoinStatus status){
        createMember(center, loginId, password, name, email, sex, rrn, phone, address, status);
    }

}
