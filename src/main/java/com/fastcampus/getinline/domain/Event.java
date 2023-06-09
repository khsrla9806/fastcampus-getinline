package com.fastcampus.getinline.domain;

import com.fastcampus.getinline.constant.EventStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
@Table(indexes = {
        @Index(columnList = "placeId"),
        @Index(columnList = "eventName"),
        @Index(columnList = "eventStartDateTime"),
        @Index(columnList = "eventEndDateTime"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    @Setter // Setter를 부분적으로 선택해서 지정하기 위해서 필드에 직접 설정
    @Column(nullable = false)
    private Long placeId;

    @Setter
    @Column(nullable = false)
    private String eventName;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Setter
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStartDateTime;

    @Setter
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEndDateTime;

    @Setter
    @Column(nullable = false)
    private Integer currentNumberOfPeople;

    @Setter
    @Column(nullable = false)
    private Integer capacity;

    @Setter
    private String memo;

    // insertable, updatable => SQL문을 작성할 때, Column에 createAt과 modifiedAt은 작성하지 않는다는 의미.
    // insert, update를 못한다는 것을 의미하지는 않는다.
    // columnDefinition은 특정 DB에 종속될 수 있는 설정이다. 나중에는 빼주는 것이 좋다.
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "timeStamp default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "timeStamp default CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected Event() {}

    // JPA에서 자동으로 입력해주는 값들은 빼고 생성자를 작성
    protected Event(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        this.placeId = placeId;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
        this.eventStartDateTime = eventStartDateTime;
        this.eventEndDateTime = eventEndDateTime;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.capacity = capacity;
        this.memo = memo;
    }

    public static Event of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new Event(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

}
