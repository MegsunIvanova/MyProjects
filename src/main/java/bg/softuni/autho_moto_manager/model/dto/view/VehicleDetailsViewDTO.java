package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;
import java.util.*;

public class VehicleDetailsViewDTO extends VehicleSummaryViewDTO {
    private boolean sold;
    private String notes;
    private List<PictureViewDTO> pictures;
    private Map<CostTypeEnum, BigDecimal> totalCostsByType;
    private BigDecimal totalCostsInBGN;

    public VehicleDetailsViewDTO(VehicleEntity vehicleEntity,
                                 List<PictureViewDTO> pictures,
                                 Map<CostTypeEnum, BigDecimal> totalCostsByType) {
        super(vehicleEntity);
        this.sold = vehicleEntity.isSold();
        this.notes = vehicleEntity.getNotes();
        this.pictures = pictures;
        this.totalCostsByType = totalCostsByType;
        this.totalCostsInBGN = vehicleEntity.getTotalCostsInBGN();
    }

    public List<PictureViewDTO> getPictures() {
        return pictures;
    }

    public boolean isSold() {
        return sold;
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

    public VehicleDetailsViewDTO setTotalCostsInBGN(BigDecimal totalCostsInBGN) {
        this.totalCostsInBGN = totalCostsInBGN;
        return this;
    }
}
