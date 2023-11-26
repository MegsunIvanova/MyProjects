package bg.softuni.autho_moto_manager.model.enums;

public enum CostTypeEnum {
    VEHICLE_AUCTION_PRICE("Vehicle auction price"),
    CONTAINER("Container"),
    AGENT_COMMISSION("Agent's' commission"),
    AUCTION_COMMISSION("Auction's' commission"),
    TRANSPORT_EXTERNAL("External transport"),
    TRANSPORT_INTERNAL("Internal transport"),
    VAT_DUTY("VAT, Duty"),
    BANK_FEES("Bank fees"),
    TECHNICAL_INSPECTION("Technical inspection fee"),
    REPAIRMENT_SERVICES("Repairment services"),
    REPAIRMENT_PARTS("Repairment parts"),
    OTHER_COSTS("Other costs");

    String formattedName;

    CostTypeEnum(String formattedName) {
        this.formattedName = formattedName;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
