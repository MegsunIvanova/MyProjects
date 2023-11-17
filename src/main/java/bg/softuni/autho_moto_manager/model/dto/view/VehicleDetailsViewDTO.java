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

    public VehicleDetailsViewDTO(VehicleEntity vehicleEntity,
                                 List<PictureViewDTO> pictures,
                                 Map<CostTypeEnum, BigDecimal> totalCostsByType) {
        super(vehicleEntity);
        this.sold = vehicleEntity.isSold();
        this.notes = vehicleEntity.getNotes();
        this.pictures = pictures;
        this.totalCostsByType = totalCostsByType;
    }

    public List<PictureViewDTO> getPictures() {
        return pictures;
    }

    public VehicleDetailsViewDTO setPictures(List<PictureViewDTO> pictures) {
        this.pictures = pictures;
        return this;
    }

    public boolean isSold() {
        return sold;
    }

    public VehicleDetailsViewDTO setSold(boolean sold) {
        this.sold = sold;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public VehicleDetailsViewDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Map<CostTypeEnum, BigDecimal> getTotalCostsByType() {
        return totalCostsByType;
    }

    public VehicleDetailsViewDTO setTotalCostsByType(Map<CostTypeEnum, BigDecimal> totalCostsByType) {
        this.totalCostsByType = totalCostsByType;
        return this;
    }

    public BigDecimal getTotalCostsInBGN () {
        return totalCostsByType.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
