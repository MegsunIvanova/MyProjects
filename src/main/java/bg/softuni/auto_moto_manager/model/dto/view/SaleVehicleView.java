package bg.softuni.auto_moto_manager.model.dto.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        return totalCostsInBGN.setScale(2, RoundingMode.HALF_UP);
    }

    public SaleVehicleView setTotalCostsInBGN(BigDecimal totalCostsInBGN) {
        this.totalCostsInBGN = totalCostsInBGN;
        return this;
    }

    public boolean isAllCostsCompleted() {
        return allCostsCompleted;
    }

    public SaleVehicleView setAllCostsCompleted(boolean allCompleteCosts) {
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

    public List<String> getCurrenciesIds() {
        return currenciesIds;
    }

    public SaleVehicleView setCurrenciesIds(List<String> currenciesIds) {
        this.currenciesIds = currenciesIds;
        return this;
    }

}
