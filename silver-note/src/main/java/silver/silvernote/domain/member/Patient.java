package silver.silvernote.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.center.Center;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Patient extends Member {

    @OneToMany(mappedBy = "patient")
    private List<Family> familyList = new ArrayList<>();

    private int grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Member manager;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Patient(Member manager, Center center,  String loginId, String password,String name,
                   String email, String sex, String rrn, String phone, String address, String zipcode, int grade, JoinStatus status){
        createMember(center, loginId, password, name, email, sex, rrn, phone, address, zipcode, status);
        this.manager = manager;
        this.grade = grade;
    }

    public void changeManager(Member manager){ this.manager = manager; }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
