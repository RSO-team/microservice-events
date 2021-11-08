package si.fri.rsoteam.models.converters;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.util.List;

public class Eventpackage si.fri.rsoteam.models.converters;
        import org.eclipse.persistence.jpa.jpql.parser.DateTime;

        import javax.persistence.*;
        import java.util.List;

@Entity
@Table(name = "Events")
public class Event implements java.io.Serializable{
    enum EventScope{
        PUBLIC,
        PRIVATE,
        SHARED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer creatorId;

    private DateTime startsAt;

    private Integer duration;

    private EventScope eventScope;

    //TODO invitees
    @ManyToMany(mappedBy = "id")
    private List<User> invitees;

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

    public DateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(DateTime startsAt) {
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

    public List<User> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<User> invitees) {
        this.invitees = invitees;
    }

    public void addNewInvitee(User newInvitee){
        this.invitees.add(newInvitee);
    }
}
 {
}
