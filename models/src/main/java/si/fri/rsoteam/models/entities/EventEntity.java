package si.fri.rsoteam.models.entities;
import si.fri.rsoteam.models.converters.InviteesAtributeConverter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "events")
public class EventEntity implements java.io.Serializable{
    enum EventScope{
        PUBLIC,
        PRIVATE,
        SHARED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer creatorId;

    private Instant startsAt;

    private Integer duration;

    private EventScope eventScope;

//    @Column
//    @Convert(converter = InviteesAtributeConverter.class)
//    private List<Integer> invitees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EventScope getEventScope() {
        return eventScope;
    }

    public void setEventScope(EventScope eventScope) {
        this.eventScope = eventScope;
    }

//    public List<Integer> getInvitees() {
//        return this.invitees;
//    }
//
//    public void setInvitees(List<Integer> invitees){
//        this.invitees = invitees;
//    }
}