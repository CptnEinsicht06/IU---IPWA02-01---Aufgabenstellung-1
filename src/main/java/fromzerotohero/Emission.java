package fromzerotohero;

import jakarta.persistence.*;

@Entity
@Table(name = "emissionen")
public class Emission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="land")
    private String land;

    @Column(name="landcode")
    private String landCode;

    @Column(name="emission1990")
    private Double emission1990;

    @Column(name="emission2000")
    private Double emission2000;

    @Column(name="emission2010")
    private Double emission2010;

    @Column(name="emission2020")
    private Double emission2020;

    // Getter/Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLand() { return land; }
    public void setLand(String land) { this.land = land; }
    public String getLandCode() { return landCode; }
    public void setLandCode(String landCode) { this.landCode = landCode; }
    public Double getEmission1990() { return emission1990; }
    public void setEmission1990(Double emission1990) { this.emission1990 = emission1990; }
    public Double getEmission2000() { return emission2000; }
    public void setEmission2000(Double emission2000) { this.emission2000 = emission2000; }
    public Double getEmission2010() { return emission2010; }
    public void setEmission2010(Double emission2010) { this.emission2010 = emission2010; }
    public Double getEmission2020() { return emission2020; }
    public void setEmission2020(Double emission2020) { this.emission2020 = emission2020; }
}

