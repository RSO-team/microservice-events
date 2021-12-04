package si.fri.rsoteam.services.mappers;

import si.fri.rsoteam.lib.dtos.EventDto;
import si.fri.rsoteam.models.entities.EventEntity;

public class EventMapper {
    public static EventDto entityToDto(EventEntity et) {
        EventDto eventDto = new EventDto();
        eventDto.id = et.getId();
        eventDto.eventScope = String.valueOf(et.getEventScope());
        eventDto.creatorId = et.getCreatorId();
        eventDto.duration = et.getDuration();
        eventDto.invitees = et.getInvitees();
        eventDto.startsAt = et.getStartsAt();

        return eventDto;
    }

    public static EventEntity dtoToEntity(EventDto eventDto) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventScope(EventEntity.EventScope.valueOf(eventDto.eventScope));
        eventEntity.setCreatorId(eventDto.creatorId);
        eventEntity.setDuration(eventDto.duration);
        eventEntity.setInvitees(eventDto.invitees);
        eventEntity.setStartsAt(eventDto.startsAt);

        return eventEntity;
    }
}
