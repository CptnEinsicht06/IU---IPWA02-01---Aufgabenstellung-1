package fromzerotohero;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Named("Table")
@RequestScoped
public class TableBean {

    private List<String> columns;
    private List<Map<String, Object>> rows;

    public TableBean() {
        load();
    }

    private void load() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
        try {
            // JDBC-Treiber sicher laden
            Class.forName("org.mariadb.jdbc.Driver");

            // Schema & Tabelle gemÃ¤ÃŸ deinem Screenshot: emission.emissionen
            String url = "jdbc:mariadb://localhost:3306/emission";
            try (Connection con = DriverManager.getConnection(url, "root", "admin");
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM emissionen LIMIT 100");
                 ResultSet rs = ps.executeQuery()) {

                ResultSetMetaData md = rs.getMetaData();
                int colCount = md.getColumnCount();
                for (int i = 1; i <= colCount; i++) {
                    columns.add(md.getColumnLabel(i));
                }
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (String c : columns) {
                        row.put(c, rs.getObject(c));
                    }
                    rows.add(row);
                }
            }
        } catch (Exception e) {
            // Zeige Fehler im UI statt weiÃŸer Seite
            columns = List.of("Fehler");
            rows = List.of(Map.of("Fehler", e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }

    public List<String> getColumns() { return columns; }
    public List<Map<String, Object>> getRows() { return rows; }
}