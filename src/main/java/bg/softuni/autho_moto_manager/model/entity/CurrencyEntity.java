package bg.softuni.autho_moto_manager.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table (name = "currencies")
public class CurrencyEntity {

    @Id
    private String id;
    @Column(name = "rate_to_BGN")
    private BigDecimal rateToBGN;

    public CurrencyEntity() {
    }

    public String getId() {
        return id;
    }

    public CurrencyEntity setId(String id) {
        this.id = id;
        return this;
    }

    public BigDecimal getRateToBGN() {
        return rateToBGN;
    }

    public CurrencyEntity setRateToBGN(BigDecimal rateToBGN) {
        this.rateToBGN = rateToBGN;
        return this;
    }
}
