package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.domain.Place;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
        assertThat(events).hasSize(7); // data.sql에서 기본적으로 6개의 데이터를 넣어주기 때문에

        // Id가 순차적으로 Auto Increment 되었는지 확인해보는 코드
        Long id = 1L;
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            assertThat(iterator.next().getId()).isEqualTo(id++);
        }
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
