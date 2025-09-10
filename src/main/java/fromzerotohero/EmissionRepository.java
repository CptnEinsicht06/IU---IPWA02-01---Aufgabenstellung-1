package fromzerotohero;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

@RequestScoped
public class EmissionRepository {

    @Inject
    EntityManager em;

    public List<Emission> listAllById() {
        return em.createQuery("select e from Emission e order by e.id", Emission.class)
                .getResultList();
    }

    public List<Emission> listAllByLand() {
        return em.createQuery("select e from Emission e order by e.land", Emission.class)
                .getResultList();
    }

    public Emission find(Integer id) {
        return em.find(Emission.class, id);
    }

    public void updateEmissions(Integer id, Double e1990, Double e2000, Double e2010, Double e2020) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Emission e = em.find(Emission.class, id);
            if (e == null) throw new IllegalArgumentException("Emission id=" + id + " nicht gefunden");
            e.setEmission1990(e1990);
            e.setEmission2000(e2000);
            e.setEmission2010(e2010);
            e.setEmission2020(e2020);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }
}