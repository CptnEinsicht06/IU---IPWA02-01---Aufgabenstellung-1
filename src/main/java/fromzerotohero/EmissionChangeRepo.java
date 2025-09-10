package fromzerotohero;

import fromzerotohero.EmissionChange.Status;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
public class EmissionChangeRepo {

    @Inject
    EntityManager em;

    public void insertChange(Integer emissionId, Double e1990, Double e2000, Double e2010, Double e2020, String proposer) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Emission base = em.find(Emission.class, emissionId);
            if (base == null) throw new IllegalArgumentException("Emission id=" + emissionId + " nicht gefunden");
            EmissionChange ch = new EmissionChange();
            ch.setEmission(base);
            ch.setNew1990(e1990);
            ch.setNew2000(e2000);
            ch.setNew2010(e2010);
            ch.setNew2020(e2020);
            ch.setProposer(proposer);
            ch.setStatus(Status.PENDING);
            em.persist(ch);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public List<EmissionChange> listPending() {
        return em.createQuery(
                        "select ch from EmissionChange ch " +
                                "join fetch ch.emission e " +
                                "where ch.status = :st order by ch.createdAt", EmissionChange.class)
                .setParameter("st", Status.PENDING)
                .getResultList();
    }

    public void approve(Integer changeId, String reviewer) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            EmissionChange ch = em.find(EmissionChange.class, changeId);
            if (ch == null || ch.getStatus() != Status.PENDING)
                throw new IllegalStateException("Change nicht gefunden oder nicht PENDING");
            Emission e = ch.getEmission();
            e.setEmission1990(ch.getNew1990());
            e.setEmission2000(ch.getNew2000());
            e.setEmission2010(ch.getNew2010());
            e.setEmission2020(ch.getNew2020());
            ch.setStatus(Status.APPROVED);
            ch.setReviewedBy(reviewer);
            ch.setReviewedAt(LocalDateTime.now());
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public void reject(Integer changeId, String reviewer, String comment) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            EmissionChange ch = em.find(EmissionChange.class, changeId);
            if (ch == null || ch.getStatus() != Status.PENDING)
                throw new IllegalStateException("Change nicht gefunden oder nicht PENDING");
            ch.setStatus(Status.REJECTED);
            ch.setReviewedBy(reviewer);
            ch.setReviewedAt(LocalDateTime.now());
            ch.setReviewComment(comment);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }
}

