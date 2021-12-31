package si.fri.rsoteam.models.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@NamedQuery(name = "User.getAll", query = "SELECT e from EventEntity e")
public class EventEntity implements java.io.Serializable {
    public enum EventScope {
        PUBLIC,
        PRIVATE,
        SHARED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    private Integer creatorId;

    private Instant startsAt;

    private Integer duration;

    private EventScope eventScope;

    @OneToMany(mappedBy = "event",cascade={CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval=true)
    private List<InviteeEntity> invitees = new ArrayList<>();

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

    public List<InviteeEntity> getinvitees() {
        return invitees;
    }

    public void setInvitees(List<InviteeEntity> invitees) {
        this.invitees = invitees;
    }

}