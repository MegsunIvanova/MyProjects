package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.PictureEntity;

public class PictureDTO {
    private String url;

    public PictureDTO(PictureEntity pictureEntity) {
        this.url = pictureEntity.getUrl();
    }

    public String getUrl() {
        return url;
    }
}
