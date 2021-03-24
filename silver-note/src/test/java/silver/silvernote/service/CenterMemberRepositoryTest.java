package silver.silvernote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.member.Manager;
import silver.silvernote.domain.member.Member;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CenterMemberRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired CenterService centerService;

    @Test
    public void join_center() throws Exception {
        //given
        Center center = createCenter("동작센터", "02-0000-0000", "없음", "대구 동성로 155", "12312");

        //when
        Long centerId = centerService.save(center);

        //then
        assertThat(centerId).isEqualTo(centerService.findOne(centerId).orElseThrow(NoSuchElementException::new).getId());

    }

    @Test
    public void join_member() {
        Center center = createCenter("동작센터", "02-0000-0000", "없음", "대구 동성로 155", "12312");

        centerService.save(center);

        Member member = createManager(center,"userC","남", "950512-1779015", "010-9921-4156", "대구 동성로 155", "12312");
        Long memberId = memberService.save(member);

        assertThat(member).isEqualTo(memberService.findOne(memberId).orElseGet(() -> fail("회원가입에 실패했습니다")));
    }

    @Test
    public void member_find() {
        Center center = createCenter("동작센터", "02-0000-0000", "없음", "대구 동성로 155", "12312");

        Member member = createManager(center,"userC", "남", "930911-1779015", "010-9921-4156", "대구 동성로 155", "12312");
        Long memberId = memberService.save(member);

        assertThat(member).isEqualTo(memberService.findOne(memberId).orElseGet(() -> fail("회원가입에 실패했습니다")));
    }

    @Test
    public void join_fail_blank(){
        Center center = createCenter("동작센터", "02-0000-0000", "없음", "대구 동성로 155", "12312");

        Member member = createManager(center,"","남", "930911-1779017", "010-9921-4156", "대구 동성로 155", "12312");

        assertThrows(ConstraintViolationException.class, () -> memberService.save(member));
    }

    @Test
    public void join_fail_dup(){
        Center center = createCenter("동작센터", "02-0000-0000", "없음", "대구 동성로 155", "12312");

        Member member = createManager(center,"", "남", "930823-1779013", "010-9921-4156", "대구 동성로 155", "12312");

        assertThrows(ConstraintViolationException.class, () -> memberService.save(member));
    }

    private Center createCenter(String name, String phone, String description, String address, String zipcode) {
        return Center.
                BuilderByParam()
                .name(name)
                .phone(phone)
                .description(description)
                .address(address)
                .zipcode(zipcode)
                .build();
    }

    private Manager createManager(Center center, String name, String sex, String rrn, String phone, String address, String zipcode) {
        return Manager.BuilderByParam()
                .center(center)
                .name(name)
                .sex(sex)
                .rrn(rrn)
                .phone(phone)
                .address(address)
                .zipcode(zipcode)
                .build();
    }
}
