package com.fastcampus.getinline.repository.querydsl;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.domain.QEvent;
import com.fastcampus.getinline.dto.EventViewResponse;
import com.fastcampus.getinline.exception.GeneralException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.getinline.constant.ErrorCode.DATA_ACCESS_ERROR;

public class EventRepositoryCustomImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {
    // 뒤에 Impl을 붙여줘야 EventRepositoryCustom의 구현체라는 것을 자동으로 인식하게 된다.

    public EventRepositoryCustomImpl() {
        super(Event.class); // QuerydslRepositorySupport를 사용하기 위해서 해당 Repository의 Entity를 생성자로 넣어줌.
    }

    @Override
    public Page<EventViewResponse> findEventViewPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        QEvent event = QEvent.event;

        JPQLQuery<EventViewResponse> query = from(event)
                .select(Projections.constructor( // 생성자를 통해 select절 내부에 Projections
                        EventViewResponse.class,
                        event.id,
                        event.place.placeName, // 단순한 join 쿼리는 직접 join()을 명시하지 않아도 된다.
                        event.eventName,
                        event.eventStatus,
                        event.eventStartDatetime,
                        event.eventEndDatetime,
                        event.currentNumberOfPeople,
                        event.capacity,
                        event.memo
                ));

        if (placeName != null && !placeName.isBlank()) {
            // like 대신 contains를 써줘야 한다.
            // 대소문자 구분을 안 하기 위해서 containsIgnoreCase를 사용
            query.where(event.place.placeName.containsIgnoreCase(placeName));
        }
        if (eventName != null && !eventName.isBlank()) {
            query.where(event.eventName.containsIgnoreCase(eventName));
        }
        if (eventStatus != null) {
            query.where(event.eventStatus.eq(eventStatus));
        }
        if (eventStartDatetime != null) {
            // eventStartDatetime 보다 크거나 같은지 (greater or equal)
            query.where(event.eventStartDatetime.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null) {
            // eventEndDatetime 보다 작거나 같은지 (lower or equal)
            query.where(event.eventStartDatetime.loe(eventEndDatetime));
        }

        List<EventViewResponse> events = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(DATA_ACCESS_ERROR, "Spring Data JPA로 부터 Querydsl 인스턴스를 못 가져옴"))
                .applyPagination(pageable, query)
                .fetch();

        // 마지막에 PAgeImpl로 페이지 정보를 넘겨준다. 이때 마지막 total값은 events의 Size가 아닌 Query Count를 넘겨줘야 한다.
        return new PageImpl<>(events, pageable, query.fetchCount());
    }
}
