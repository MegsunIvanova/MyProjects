package bg.softuni.auto_moto_manager.model.dto.view;

import bg.softuni.auto_moto_manager.model.entity.VehicleEntity;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;
import java.util.*;

public class VehicleDetailsViewDTO extends VehicleSummaryViewDTO {
    private final String notes;
    private final List<PictureViewDTO> pictures;
    private final Map<CostTypeEnum, BigDecimal> totalCostsByType;
    private final BigDecimal totalCostsInBGN;
    private final SaleViewDTO sale;

    public VehicleDetailsViewDTO(VehicleEntity vehicleEntity,
                                 List<PictureViewDTO> pictures,
                                 Map<CostTypeEnum, BigDecimal> totalCostsByType,
                                 SaleViewDTO sale) {
        super(vehicleEntity);
        this.notes = vehicleEntity.getNotes();
        this.pictures = pictures;
        this.totalCostsByType = totalCostsByType;
        this.totalCostsInBGN = vehicleEntity.getTotalCostsInBGN();
        this.sale = sale;
    }

    public List<PictureViewDTO> getPictures() {
        return pictures;
    }

    public String getNotes() {
        return notes;
    }

    public Map<CostTypeEnum, BigDecimal> getTotalCostsByType() {
        return totalCostsByType;
    }

    public BigDecimal getTotalCostsInBGN() {
        return totalCostsInBGN;
    }

    public boolean isSold() {
        return this.sale != null;
    }

    public SaleViewDTO getSale() {
        return sale;
    }

    public BigDecimal getSaleResult() {
        return isSold()
                ? this.sale.getSalePriceInBGN().subtract(this.totalCostsInBGN)
                : null;
    }
}
