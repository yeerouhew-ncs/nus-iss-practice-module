package tbs.tbsapi.dto;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@AttributeOverrides({
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY")),
        @AttributeOverride(name = "updatedDate", column = @Column(name = "UPDATED_DATE")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))
})
public class CreatedUpdatedColumn {
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "VERSION")
    private Integer version;

    @PrePersist
    public void beforeInsert() {
        if (createdBy == null) this.createdBy = "SYSTEM";
        if (createdDate == null) this.createdDate = LocalDateTime.now();
        if (updatedBy == null) this.updatedBy = "SYSTEM";
        if (updatedDate == null) this.updatedDate = LocalDateTime.now();
        if (version == null) this.version = 1;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
