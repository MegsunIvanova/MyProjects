package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;

import java.util.ArrayList;
import java.util.List;

public class VehicleDetailsViewDTO extends VehicleSummaryViewDTO {

    private List<PictureDTO> pictures;
    private List<CostDTO> costCalculation;
    private boolean sold;
    private String notes;

    public VehicleDetailsViewDTO(VehicleEntity vehicleEntity) {
        super(vehicleEntity);
        pictures = vehicleEntity.getPictures().stream().map(PictureDTO::new).toList();
        costCalculation = vehicleEntity.getCostCalculation().stream().map(CostDTO::new).toList();
        this.sold = vehicleEntity.isSold();
        this.notes = vehicleEntity.getNotes();
    }

    public List<PictureDTO> getPictures() {
        return pictures;
    }

    public List<CostDTO> getCostCalculation() {
        return costCalculation;
    }

    public boolean isSold() {
        return sold;
    }

    public String getNotes() {
        return notes;
    }
}
