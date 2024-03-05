package com.example.tosshelperappserver.service.tag;


import com.example.tosshelperappserver.config.exception.custom.AlreadyExistElementException;
import com.example.tosshelperappserver.config.exception.custom.NoSuchElementFoundException;
import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;
import com.example.tosshelperappserver.dto.tag.MemberWithTagDto;
import com.example.tosshelperappserver.dto.tag.TagDto;
import com.example.tosshelperappserver.dto.tag.TagRequestDto;
import com.example.tosshelperappserver.service.member.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional // 각각의 테스트 메서드에 대해 트랜잭션을 시작하고, 테스트가 종료되면 롤백
class TagServiceTest {

    @Autowired
    private TagService tagService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    private Long memberId1;
    private Long memberId2;

    @BeforeEach
    public void before(){
        MemberJoinRequestDto member = new MemberJoinRequestDto("hong", "홍길동", "1111");
        MemberJoinRequestDto member2 = new MemberJoinRequestDto("kim", "김길동", "1111");
        memberId1 = memberService.addMember(member);
        memberId2 = memberService.addMember(member2);
    }

    @Test
    @DisplayName("태그 추가 성공")
    public void successTagInsert() throws Exception {
        // Given
        TagRequestDto request = new TagRequestDto("태그 이름", "태그 컨텐츠");

        // When
        Long insertTagId = tagService.addTag(request, memberId1);

        // Then
        assertThat(insertTagId).isNotNull();
    }

    @Test
    @DisplayName("태그 추가 실패 : 존재하지 않는 회원")
    public void failTagInsertCaseNotExistMember() throws Exception {
        // Given
        TagRequestDto request = new TagRequestDto("태그 이름", "태그 컨텐츠");

        // When & Then
        assertThrows(NoSuchElementFoundException.class, () -> {
            Long insertTagId = tagService.addTag(request, Long.MAX_VALUE);
        });
    }


    @Test
    @DisplayName("태그 추가 실패 : 해당 회원에 중복된 태그이름이 존재")
    public void failTagInsertCaseDuplicateName() throws Exception {
        // Given
        TagRequestDto exist = new TagRequestDto("중복 태그", "태그 컨텐츠");
        Long existTagId = tagService.addTag(exist, memberId1);
        TagRequestDto request = new TagRequestDto("중복 태그", "태그 컨텐츠");

        // When & Then
        assertThrows(AlreadyExistElementException.class, () -> {
            Long insertTagId = tagService.addTag(request, memberId1);
        });
    }

    @Test
    @DisplayName("태그 조회 : 해당 회원에 해당하는 태그 조회 성공")
    public void successTagSelectByMemberV1() throws Exception {
        // Given
        TagRequestDto tag1 = new TagRequestDto("태그1", "태그 컨텐츠");
        TagRequestDto tag2 = new TagRequestDto("태그2", "태그 컨텐츠");
        Long tag1Id = tagService.addTag(tag1, memberId1);
        Long tag2Id = tagService.addTag(tag2, memberId2);


        // 엔티티를 추가한 후 영속성 컨텍스트를 비우고 변경사항을 즉시 데이터베이스에 반영 ->
        // when에서 영속성 컨텍스트에 존재하지 않는 엔티티를 사용하지 않고, 실제로 데이터베이스에 존재하는 엔티티를 조회
        em.clear();
        em.flush();

        // When
        MemberWithTagDto result = tagService.getTagInfoByMember(memberId1);

        // Then
        assertThat(result.getTags().size()).isEqualTo(1);
        assertThat(result.getTags().get(0).getTagId()).isEqualTo(tag1Id);
        assertThat(result.getTags().get(0).getTagId()).isNotEqualTo(tag2Id);

    }


    @Test
    @DisplayName("태그들 조회 : 해당 회원에 해당하는 태그들 조회 성공")
    public void successTagSelectByMemberV2() throws Exception {
        // Given
        TagRequestDto tag1 = new TagRequestDto("태그1", "태그 컨텐츠");
        Long tag1Id = tagService.addTag(tag1, memberId1);
        TagRequestDto tag2 = new TagRequestDto("태그2", "태그 컨텐츠");
        Long tag2Id = tagService.addTag(tag2, memberId1);


        // 엔티티를 추가한 후 영속성 컨텍스트를 비우고 변경사항을 즉시 데이터베이스에 반영 ->
        // when에서 영속성 컨텍스트에 존재하지 않는 엔티티를 사용하지 않고, 실제로 데이터베이스에 존재하는 엔티티를 조회
        em.clear();
        em.flush();

        // When
        MemberWithTagDto result = tagService.getTagInfoByMember(memberId1);

        // Then
        assertThat(result.getTags().get(0).getTagId()).isEqualTo(tag1Id);
        assertThat(result.getTags().get(1).getTagId()).isEqualTo(tag2Id);

    }

    @Test
    @DisplayName("태그들 조회 : 해당 회원에 해당하는 태그들 조회 성공")
    public void successTagSelectByMemberV3() throws Exception {
        // Given
        TagRequestDto tag1 = new TagRequestDto("태그1", "태그 컨텐츠");
        Long tag1Id = tagService.addTag(tag1, memberId1);
        TagRequestDto tag2 = new TagRequestDto("태그2", "태그 컨텐츠");
        Long tag2Id = tagService.addTag(tag2, memberId1);


        // 엔티티를 추가한 후 영속성 컨텍스트를 비우고 변경사항을 즉시 데이터베이스에 반영 ->
        // when에서 영속성 컨텍스트에 존재하지 않는 엔티티를 사용하지 않고, 실제로 데이터베이스에 존재하는 엔티티를 조회
        em.clear();
        em.flush();

        // When
        MemberWithTagDto result = tagService.getTagInfoByMember(memberId1);

        // Then
        assertThat(result.getTags())
                .extracting(TagDto::getTagId)
                .contains(tag1Id, tag2Id);

//        assertThat(result.getTags())
//                .extracting(e -> e.getTagId())
//                .contains(tag1Id, tag2Id);

        String[] actualList = {"재휘", "철수", "영희"};
        assertThat(actualList).contains("철수", "영희");
    }
}