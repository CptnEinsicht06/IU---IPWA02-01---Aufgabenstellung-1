package fromzerotohero;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emissionen_changes")
public class EmissionChange {

    public enum Status { PENDING, APPROVED, REJECTED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emission_id")
    private Emission emission;

    @Column(name="new_emission1990") private Double new1990;
    @Column(name="new_emission2000") private Double new2000;
    @Column(name="new_emission2010") private Double new2010;
    @Column(name="new_emission2020") private Double new2020;

    @Column(nullable=false) private String proposer;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false) private Status status = Status.PENDING;

    @Column(name="created_at")  private LocalDateTime createdAt;
    @Column(name="reviewed_by") private String reviewedBy;
    @Column(name="reviewed_at") private LocalDateTime reviewedAt;
    @Column(name="review_comment", length=500) private String reviewComment;

    @Transient
    private String tempComment;

    @PrePersist
    public void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }

    // Getter/Setter
    public Integer getId() { return id; }
    public Emission getEmission() { return emission; }
    public void setEmission(Emission emission) { this.emission = emission; }
    public Double getNew1990() { return new1990; }
    public void setNew1990(Double new1990) { this.new1990 = new1990; }
    public Double getNew2000() { return new2000; }
    public void setNew2000(Double new2000) { this.new2000 = new2000; }
    public Double getNew2010() { return new2010; }
    public void setNew2010(Double new2010) { this.new2010 = new2010; }
    public Double getNew2020() { return new2020; }
    public void setNew2020(Double new2020) { this.new2020 = new2020; }
    public String getProposer() { return proposer; }
    public void setProposer(String proposer) { this.proposer = proposer; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public String getReviewComment() { return reviewComment; }
    public void setReviewComment(String reviewComment) { this.reviewComment = reviewComment; }
    public String getTempComment() { return tempComment; }
    public void setTempComment(String tempComment) { this.tempComment = tempComment; }
}