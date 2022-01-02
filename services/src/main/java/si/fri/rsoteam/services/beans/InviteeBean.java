package si.fri.rsoteam.services.beans;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rsoteam.lib.dtos.InviteeDto;
import si.fri.rsoteam.models.entities.InviteeEntity;
import si.fri.rsoteam.services.mappers.InviteeMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class InviteeBean {
    private Logger log = LogManager.getLogger(InviteeBean.class.getName());

    @Inject
    private EntityManager em;

    @Timed
    public InviteeDto getInvitee(Integer id){
        return InviteeMapper.entityToDto(em.find(InviteeEntity.class,id));
    }

    @Timed
    public List<InviteeDto> getAllInvitees(){
        return em.createNamedQuery("Invitee.getAll", InviteeEntity.class).getResultList().stream().map(InviteeMapper::entityToDto).collect(Collectors.toList());
    }

    public void deleteInvitee(Integer id) {
        InviteeEntity inviteeEntity = em.find(InviteeEntity.class, id);
        if ( inviteeEntity != null) {
            this.beginTx();
            em.remove(inviteeEntity);
            this.commitTx();
        }
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
