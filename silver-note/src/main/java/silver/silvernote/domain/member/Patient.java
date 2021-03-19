package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Patient extends Member {

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Family> familyList = new ArrayList<>();

    private int grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member manager;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Patient(Member manager, Center center, String name,  String sex, String rrn, String phone, Address address, int grade, JoinStatus status){
        createMember(center, name, sex, rrn, phone, address, status);
        this.manager = manager;
        this.grade = grade;
    }
}
