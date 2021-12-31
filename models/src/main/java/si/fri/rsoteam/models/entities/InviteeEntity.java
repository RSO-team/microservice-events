package si.fri.rsoteam.models.entities;

import jdk.jfr.Event;

import javax.persistence.*;

@Entity
@Table(name = "invitee")
@NamedQuery(name = "Invitee.getAll", query = "SELECT invitee from InviteeEntity invitee")
public class InviteeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
