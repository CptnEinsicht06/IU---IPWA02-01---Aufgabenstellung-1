package fromzerotohero;

import jakarta.annotation.PostConstruct;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("emEdit")
@ViewScoped
public class EmissionEditBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer selectedId;
    private List<SelectItem> options;

    private Double emission1990, emission2000, emission2010, emission2020;
    private String message;

    @Inject private EmissionRepository emissionRepo;
    @Inject private EmissionChangeRepo changeRepo;
    @Inject private LoginUser loginUser;

    @PostConstruct
    public void init() {
        options = new ArrayList<>();
        try {
            for (Emission e : emissionRepo.listAllByLand()) {
                options.add(new SelectItem(e.getId(), e.getLand() + " (" + e.getLandCode() + ")"));
            }
        } catch (Exception e) {
            message = "Fehler beim Laden der LÃ¤nder: " + e.getMessage();
        }
    }

    public void loadRow() {
        message = null;
        if (selectedId == null) { message = "Bitte zuerst ein Land wÃ¤hlen."; return; }
        Emission e = emissionRepo.find(selectedId);
        if (e == null) { message = "Datensatz nicht gefunden (ID " + selectedId + ")"; return; }
        emission1990 = e.getEmission1990();
        emission2000 = e.getEmission2000();
        emission2010 = e.getEmission2010();
        emission2020 = e.getEmission2020();
    }

    public void save() {
        message = null;
        if (selectedId == null) { message = "Bitte zuerst ein Land wÃ¤hlen."; return; }
        try {
            if (loginUser != null && loginUser.isAdmin()) {
                emissionRepo.updateEmissions(selectedId, emission1990, emission2000, emission2010, emission2020);
                message = "Aktualisiert (Admin).";
            } else {
                String user = (loginUser != null && loginUser.getUsername() != null)
                        ? loginUser.getUsername() : "user";
                changeRepo.insertChange(selectedId, emission1990, emission2000, emission2010, emission2020, user);
                message = "Ã„nderung eingereicht. Wartet auf Freigabe durch Admin.";
            }
        } catch (Exception e) {
            message = "Speichern fehlgeschlagen: " + e.getMessage();
        }
    }

    // Getter/Setter
    public Integer getSelectedId() { return selectedId; }
    public void setSelectedId(Integer selectedId) { this.selectedId = selectedId; }
    public List<SelectItem> getOptions() { return options; }
    public Double getEmission1990() { return emission1990; }
    public void setEmission1990(Double v) { this.emission1990 = v; }
    public Double getEmission2000() { return emission2000; }
    public void setEmission2000(Double v) { this.emission2000 = v; }
    public Double getEmission2010() { return emission2010; }
    public void setEmission2010(Double v) { this.emission2010 = v; }
    public Double getEmission2020() { return emission2020; }
    public void setEmission2020(Double v) { this.emission2020 = v; }
    public String getMessage() { return message; }
}


