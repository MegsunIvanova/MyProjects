package bg.softuni.auto_moto_manager.model.dto.view;

import bg.softuni.auto_moto_manager.model.entity.PictureEntity;

public class PictureViewDTO {
    private final String url;

    public PictureViewDTO(PictureEntity pictureEntity) {
        this.url = pictureEntity.getUrl();
    }

    public String getUrl() {
        return url;
    }
}
