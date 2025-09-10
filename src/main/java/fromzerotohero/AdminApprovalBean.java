package fromzerotohero;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("adminApproval")
@ViewScoped
public class AdminApprovalBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject private EmissionChangeRepo changeRepo;
    @Inject private LoginUser loginUser;

    private List<EmissionChange> pending;

    @PostConstruct
    public void init() { reload(); }

    public void reload() {
        pending = changeRepo.listPending();
    }

    public void approve(int id) {
        String reviewer = (loginUser != null ? loginUser.getUsername() : "admin");
        changeRepo.approve(id, reviewer);
        reload();
    }

    public void reject(int id) {
        String comment = null;
        if (pending != null) {
            for (EmissionChange r : pending) {
                if (r.getId() == id) { comment = r.getTempComment(); break; }
            }
        }
        String reviewer = (loginUser != null ? loginUser.getUsername() : "admin");
        changeRepo.reject(id, reviewer, comment);
        reload();
    }

    public List<EmissionChange> getPending() { return pending; }
}


