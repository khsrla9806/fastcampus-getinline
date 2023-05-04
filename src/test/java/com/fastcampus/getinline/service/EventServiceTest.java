package com.fastcampus.getinline.service;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.dto.EventDto;
import com.fastcampus.getinline.dto.EventResponse;
import com.fastcampus.getinline.exception.GeneralException;
import com.fastcampus.getinline.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.getinline.constant.EventStatus.OPENED;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks // Mock을 주입할 필드에 붙여줌
    private EventService sut; // System Under Test 라는 관례 단어라고 함.

    // Mock으로 주입할 필드에 붙여줌
    // 이렇게 붙여주면 EventRepository를 EventService에 주입해준다.
    @Mock
    private EventRepository eventRepository;

    @DisplayName("검색 조건 없이 이벤트 검색 시, 전체 결과를 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnsEntireEventList() {
        // Given
        given(eventRepository.findEvents(null, null, null, null, null))
                .willReturn(List.of(
                        createEventDto(1L, "오전 운동", true),
                        createEventDto(1L, "오후 운동", false)
                ));

        // When
        List<EventDto> list = sut.getEvents(null, null, null, null, null);

        // Then
        assertThat(list).hasSize(2);
        verify(eventRepository, times(1)).findEvents(null, null, null, null, null);
    }

    @Test
    void givenRuntimeException_whenSearchingEvents_thenReturnsGeneralException() {
        // Given
        RuntimeException exception = new RuntimeException("test message");
        given(eventRepository.findEvents(any(), any(), any(), any(), any()))
                .willThrow(exception);

        // When
        Throwable thrown = catchThrowable(() -> sut.getEvents(any(), any(), any(), any(), any()));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        verify(eventRepository, times(1)).findEvents(any(), any(), any(), any(), any());
    }

    @DisplayName("검색 조건과 함께 이벤트 검색 시, 검색된 결과를 보여준다.")
    @Test
    void givenSearchParams_whenSearchingEvents_thenReturnsEntireEventList() {
        // Given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2023, 4, 20, 0, 0, 0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2023, 4, 21, 0, 0, 0);

        given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime))
                .willReturn(List.of(
                        createEventDto(1L, "오전 운동", eventStatus, eventStartDatetime, eventEndDatetime)
                ));

        // When
        List<EventDto> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

        // Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> { // 결과의 모든 내용이 해당 블럭 안의 내용에 부합되는지 검사한다.
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);

                    assertThat(event.getEventStartDateTime()).isAfterOrEqualTo(eventStartDatetime); // 내가 검색한 시간의 이후이거나 같다.
                    assertThat(event.getEventStartDateTime()).isBeforeOrEqualTo(eventEndDatetime); // 내가 검색한 시간의 이전이거나 같다.
                });

        verify(eventRepository, times(1)).findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingExistingEvent_thenReturnsOptional() {
        // Given
        long eventId = 1L;
        EventDto eventDto = createEventDto(1L, "오전 운동", true);

        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDto));

        // When
        Optional<EventDto> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(eventDto); // value가 일치하는가?
        verify(eventRepository, times(1)).findEvent(eventId);
    }

    @DisplayName("이벤트 ID로 존재하지 않는 이벤트를 조회하면, 빈 정보를 출력하여 보여준다")
    @Test
    void givenEventId_whenSearchingNoneExistingEvent_thenReturnsEmptyOptional() {
        // Given
        long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        // When
        Optional<EventDto> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();
        verify(eventRepository, times(1)).findEvent(eventId);
    }

    @DisplayName("이벤트 정보를 주면, 이벤트 정보를 생성히고, EventResponse를 반환한다.")
    @Test
    void givenEventInfo_whenCreating_thenCreateEventAndReturnsEventResponse() {
        // Given
        EventDto eventDto = createEventDto(1L,
                "패캠 강의",
                OPENED,
                LocalDateTime.now(),
                LocalDateTime.now());
        given(eventRepository.save(any())).willReturn(any(Event.class));

        // When
        EventResponse result = sut.createEvent(eventDto);

        // Then
        assertThat(result).isInstanceOf(EventResponse.class);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @DisplayName("이벤트 정보를 주지 않으면, 이벤트 정보를 생성을 중단하고, Exception을 발생")
    @Test
    void givenNothing_whenCreating_thenThrowsNullPointerException() {
        // Given

        // When & Then
        assertThatThrownBy(() -> sut.createEvent(null)).isInstanceOf(NullPointerException.class);
        verify(eventRepository, times(0)).save(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenReturnsTrue() {
        // Given
        long eventId = 1L;
        EventDto eventDto = createEventDto(1L, "오전 운동", true);
        given(eventRepository.updateEvent(eventId, eventDto)).willReturn(true);

        // When
        boolean result = sut.modifyEvent(eventId, eventDto);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository, times(1)).updateEvent(eventId, eventDto);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 반환한다.")
    @Test
    void givenNoEventId_whenModifying_thenReturnsFalse() {
        // Given
        EventDto eventDto = createEventDto(1L, "오전 운동", false);
        given(eventRepository.updateEvent(null, eventDto)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(null, eventDto);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository, times(1)).updateEvent(null, eventDto);
    }

    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 반환한다.")
    @Test
    void givenEventIdOnly_whenModifying_thenReturnsFalse() {
        // Given
        long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository, times(1)).updateEvent(eventId, null);
    }

    @DisplayName("이벤트 ID를 주면, 이벤트를 삭제하고 결과를 true를 반환한다.")
    @Test
    void givenEventId_whenDeleting_thenDeleteEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);

        // When
        boolean result = sut.deleteEvent(eventId);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository, times(1)).deleteEvent(eventId);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트를 삭제를 중단하고 결과를 true를 반환한다.")
    @Test
    void givenNoEventId_whenDeleting_thenReturnsFalse() {
        // Given
        given(eventRepository.deleteEvent(null)).willReturn(false);

        // When
        boolean result = sut.deleteEvent(null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository, times(1)).deleteEvent(null);
    }

    private EventDto createEventDto(Long placeId, String eventName, boolean isMorning) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDto(
                placeId,
                eventName,
                OPENED,
                LocalDateTime.parse(String.format("2023-04-20T%s:00:00", hourStart)),
                LocalDateTime.parse(String.format("2023-04-20T%s:00:00", hourEnd))
        );
    }

    private EventDto createEventDto(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime
    ) {
        return EventDto.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요.",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}