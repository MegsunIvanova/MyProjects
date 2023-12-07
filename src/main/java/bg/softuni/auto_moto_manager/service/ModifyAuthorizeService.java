package bg.softuni.auto_moto_manager.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ModifyAuthorizeService {

    boolean isPermitted(String uuid, UserDetails viewer);

    boolean isPermitted(String uuid, UserDetails viewer, Long costId);
}
