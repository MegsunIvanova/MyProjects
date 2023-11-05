package bg.softuni.autho_moto_manager.service;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);
}
