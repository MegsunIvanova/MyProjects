package bg.softuni.autho_moto_manager.model.dto.view;

import java.math.BigDecimal;
import java.util.List;

public class SellVehicleView {
    private String uuid;
    private String title;

    private String primaryImage;
    private BigDecimal totalCostsInBGN;
    private boolean allCostsCompleted;

    private List<String> curenciesIds;

    public SellVehicleView() {
    }

    public String getUuid() {
        return uuid;
    }

    public SellVehicleView setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SellVehicleView setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getTotalCostsInBGN() {
        return totalCostsInBGN;
    }

    public SellVehicleView setTotalCostsInBGN(BigDecimal totalCostsInBGN) {
        this.totalCostsInBGN = totalCostsInBGN;
        return this;
    }

    public boolean isAllCompleteCosts() {
        return allCostsCompleted;
    }

    public SellVehicleView setAllCompleteCosts(boolean allCompleteCosts) {
        this.allCostsCompleted = allCompleteCosts;
        return this;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public SellVehicleView setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
        return this;
    }

    public boolean isAllCostsCompleted() {
        return allCostsCompleted;
    }

    public SellVehicleView setAllCostsCompleted(boolean allCostsCompleted) {
        this.allCostsCompleted = allCostsCompleted;
        return this;
    }

    public List<String> getCurenciesIds() {
        return curenciesIds;
    }

    public SellVehicleView setCurrenciesIds(List<String> curenciesIds) {
        this.curenciesIds = curenciesIds;
        return this;
    }
}
