package si.fri.rsoteam.services.mappers;

import si.fri.rsoteam.lib.dtos.EventDto;
import si.fri.rsoteam.models.entities.EventEntity;

import java.util.stream.Collectors;

public class EventMapper {
    public static EventDto entityToDto(EventEntity et) {
        EventDto eventDto = new EventDto();
        eventDto.id = et.getId();
        eventDto.eventScope = String.valueOf(et.getEventScope());
        eventDto.creatorId = et.getCreatorId();
        eventDto.duration = et.getDuration();
        eventDto.invitees = et.getinvitees().stream().map(InviteeMapper::entityToDto).collect(Collectors.toList());
        eventDto.startsAt = et.getStartsAt();

        return eventDto;
    }

    public static EventEntity dtoToEntity(EventDto eventDto) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventScope(EventEntity.EventScope.valueOf(eventDto.eventScope));
        eventEntity.setCreatorId(eventDto.creatorId);
        eventEntity.setDuration(eventDto.duration);
        eventEntity.setStartsAt(eventDto.startsAt);
        eventEntity.setInvitees(eventDto.invitees.stream().map(InviteeMapper::dtoToEntity).collect(Collectors.toList()));
        eventEntity.getinvitees().forEach(inviteeEntity -> inviteeEntity.setEvent(eventEntity));
        return eventEntity;
    }
}
