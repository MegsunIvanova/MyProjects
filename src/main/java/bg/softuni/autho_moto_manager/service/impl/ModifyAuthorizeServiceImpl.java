package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.repository.VehicleRepository;
import bg.softuni.autho_moto_manager.service.ModifyAuthorizeService;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ModifyAuthorizeServiceImpl implements ModifyAuthorizeService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CostRepository costRepository;

    public ModifyAuthorizeServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, CostRepository costRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.costRepository = costRepository;
    }

    @Override
    public boolean isPermitted(String uuid, UserDetails viewer) {
        if (viewer == null) {
            return false;
        }

        VehicleEntity vehicleEntity = vehicleRepository.findByUuid(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Vehicle with UUID: " + uuid + " was not found!"));

        if (vehicleEntity.getSale() != null) {
            return false;
        }

        UserEntity viewerEntity = userRepository.findByEmail(viewer.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("User with email: " + viewer.getUsername() + " was not found!"));


        return isOwner(viewerEntity, vehicleEntity.getOwner());
    }

    @Override
    public boolean isPermitted(String uuid, UserDetails viewer, Long costId) {
        CostEntity cost = costRepository.findById(costId)
                .orElseThrow(() -> new ObjectNotFoundException("Cost with id: " + costId + " was not found!"));

        boolean hasPermission = isPermitted(uuid, viewer);

        return !cost.isCompleted() && hasPermission;
    }

    private boolean isOwner(UserEntity viewer, UserEntity owner) {
        boolean isAdmin = viewer.getRoles()
                .stream()
                .map(RoleEntity::getRole)
                .anyMatch(r -> r.equals(UserRoleEnum.ADMIN));

        if (isAdmin) {
            return true;
        } else {
            return owner.getEmail().equals(viewer.getEmail());
        }
    }
}
