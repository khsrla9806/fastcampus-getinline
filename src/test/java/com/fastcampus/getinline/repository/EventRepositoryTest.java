package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.domain.Place;
import com.fastcampus.getinline.dto.EventViewResponse;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Transactional의 Rollback 기능을 자동으로 탑재
public class EventRepositoryTest {
    private final EventRepository eventRepository;
    private final TestEntityManager testEntityManager;

    public EventRepositoryTest(
            @Autowired EventRepository eventRepository,
            @Autowired TestEntityManager testEntityManager
    ) {
        this.eventRepository = eventRepository;
        this.testEntityManager = testEntityManager;
    }

    @Test
    void givenSearchParams_whenFindingEventViewResponse_thenReturnsEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> result = eventRepository.findEventViewPageBySearchParams(
                "배드민턴", // 부분 일치 테스트
                "운동1", // 완전 일치 테스트
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 2, 0, 0, 0),
                PageRequest.of(0, 5) // 0번째 페이지, 5개의 자료만
        );

        // Then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0))
                .hasFieldOrPropertyWithValue("placeName", "서울 배드민턴장")
                .hasFieldOrPropertyWithValue("eventName", "운동1")
                .hasFieldOrPropertyWithValue("eventStatus", EventStatus.OPENED)
                .hasFieldOrPropertyWithValue("eventStartDatetime", LocalDateTime.of(2021, 1, 1, 9, 0, 0))
                .hasFieldOrPropertyWithValue("eventEndDatetime", LocalDateTime.of(2021, 1, 1, 12, 0, 0));
    }

    @Test
    void test() {
        // Given
        Place place = createPlace();
        Event event = createEvent(place);
        testEntityManager.persist(place); // place를 영속화 (Id 생성)
        testEntityManager.persist(event); // event를 영속화 (Id 생성)

        // When
        // BooleanBuilder는 Predicate 인터페이스의 구현체 중 하나
        Iterable<Event> events = eventRepository.findAll(new BooleanBuilder());

        // Then
        assertThat(events).hasSize(27); // data.sql에서 기본적으로 6개의 데이터를 넣어주기 때문에
    }

    private Event createEvent(Place place) {
        return Event.of(
                place,
                "Spring Data JPA 테스트",
                EventStatus.OPENED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                0,
                20,
                "메모"
        );
    }

    private Place createPlace() {
        return Place.of(
                PlaceType.COMMON,
                "패스트 캠퍼스",
                "서울시 강남구",
                "010-1234-1234",
                50,
                "메모"
        );
    }
}
