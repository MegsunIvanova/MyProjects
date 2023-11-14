package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;

import java.util.List;

public class VehicleDetailsViewDTO extends VehicleSummaryViewDTO {

    private List<PictureViewDTO> pictures;
    private List<CostViewDTO> costCalculation;
    private boolean sold;
    private String notes;

    public VehicleDetailsViewDTO(VehicleEntity vehicleEntity) {
        super(vehicleEntity);
        pictures = vehicleEntity.getPictures().stream().map(PictureViewDTO::new).toList();
        costCalculation = vehicleEntity.getCostCalculation().stream().map(CostViewDTO::new).toList();
        this.sold = vehicleEntity.isSold();
        this.notes = vehicleEntity.getNotes();
    }

    public List<PictureViewDTO> getPictures() {
        return pictures;
    }

    public List<CostViewDTO> getCostCalculation() {
        return costCalculation;
    }

    public boolean isSold() {
        return sold;
    }

    public String getNotes() {
        return notes;
    }
}
