package fromzerotohero;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Named("emissionBean")
@ViewScoped
public class EmissionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject private EmissionRepository repo;

    private List<Emission> rows = new ArrayList<>();
    private String error;
    private String sortColumn = "id";
    private boolean ascending = true;

    @PostConstruct
    public void init() {
        try {
            rows = repo.listAllById();
            sortColumn = "id";
            ascending = true;
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void sort(String col) {
        if (col == null) return;
        if (col.equals(sortColumn)) ascending = !ascending; else { sortColumn = col; ascending = true; }
        Comparator<Emission> cmp = switch (sortColumn) {
            case "land"         -> Comparator.comparing(Emission::getLand, Comparator.nullsLast(String::compareToIgnoreCase));
            case "landcode"     -> Comparator.comparing(Emission::getLandCode, Comparator.nullsLast(String::compareToIgnoreCase));
            case "emission1990" -> Comparator.comparing(Emission::getEmission1990, Comparator.nullsLast(Double::compareTo));
            case "emission2000" -> Comparator.comparing(Emission::getEmission2000, Comparator.nullsLast(Double::compareTo));
            case "emission2010" -> Comparator.comparing(Emission::getEmission2010, Comparator.nullsLast(Double::compareTo));
            case "emission2020" -> Comparator.comparing(Emission::getEmission2020, Comparator.nullsLast(Double::compareTo));
            default             -> Comparator.comparing(Emission::getId, Comparator.nullsLast(Integer::compareTo));
        };
        rows.sort(ascending ? cmp : cmp.reversed());
    }

    public String ind(String col) { return col != null && col.equals(sortColumn) ? (ascending ? " â–²" : " â–¼") : ""; }

    public List<Emission> getRows() { return rows; }
    public int getTotal() { return rows != null ? rows.size() : 0; }
    public String getError() { return error; }
    public String getSortColumn() { return sortColumn; }
    public boolean isAscending() { return ascending; }
}
