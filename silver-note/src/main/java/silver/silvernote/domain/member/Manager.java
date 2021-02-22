package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;

import javax.persistence.*;

@Entity
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Manager extends Member {

    @Enumerated(EnumType.STRING)
    private PermissionStatus permission;

    @PrePersist // default ¼¼ÆÃ
    public void prePersist() {
        this.permission = this.permission == null ? PermissionStatus.WAITING : this.permission;
        super.prePersist();
    }

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Manager(Center center, String name, String sex, String rrn, String phone, Address address, JoinStatus status){
        createMember(center, name, sex, rrn, phone, address, status);
    }

    public void updatePermission(PermissionStatus permission){ this.permission = permission; }
}
