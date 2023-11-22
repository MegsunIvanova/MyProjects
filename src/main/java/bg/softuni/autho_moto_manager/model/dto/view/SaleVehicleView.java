package bg.softuni.autho_moto_manager.model.dto.view;

import java.math.BigDecimal;
import java.util.List;

public class SaleVehicleView {
    private String uuid;
    private String title;

    private String primaryImage;
    private BigDecimal totalCostsInBGN;
    private boolean allCostsCompleted;

    private List<String> currenciesIds;

    public SaleVehicleView() {
    }

    public String getUuid() {
        return uuid;
    }

    public SaleVehicleView setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SaleVehicleView setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getTotalCostsInBGN() {
        return totalCostsInBGN;
    }

    public SaleVehicleView setTotalCostsInBGN(BigDecimal totalCostsInBGN) {
        this.totalCostsInBGN = totalCostsInBGN;
        return this;
    }

    public boolean isAllCompleteCosts() {
        return allCostsCompleted;
    }

    public SaleVehicleView setAllCompleteCosts(boolean allCompleteCosts) {
        this.allCostsCompleted = allCompleteCosts;
        return this;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public SaleVehicleView setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
        return this;
    }

    public boolean isAllCostsCompleted() {
        return allCostsCompleted;
    }

    public SaleVehicleView setAllCostsCompleted(boolean allCostsCompleted) {
        this.allCostsCompleted = allCostsCompleted;
        return this;
    }

    public List<String> getCurrenciesIds() {
        return currenciesIds;
    }

    public SaleVehicleView setCurrenciesIds(List<String> curenciesIds) {
        this.currenciesIds = curenciesIds;
        return this;
    }
}
