package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.center.Center;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Manager extends Member {


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Manager(Center center, String loginId, String password, String name, String email, String sex, String rrn, String phone, String address, String zipcode, JoinStatus status){
        createMember(center, loginId, password, name, email, sex, rrn, phone, address, zipcode, status);
    }
}
