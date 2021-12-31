package si.fri.rsoteam.lib.dtos;

import java.time.Instant;
import java.util.List;

public class EventDto {
    public Integer id;
    public Integer creatorId;
    public Instant startsAt;
    public Integer duration;
    public String eventScope;
    public List<InviteeDto> invitees;

    public EventDto() {
    }
}
