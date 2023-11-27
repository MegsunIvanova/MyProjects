package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.SaleRepository;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.repository.VehicleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Optional;

@Configuration
public class ModifyingPermissionInterceptor implements HandlerInterceptor {
    private final SaleRepository saleRepository;
    private final CostRepository costRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public ModifyingPermissionInterceptor(SaleRepository saleRepository,
                                          CostRepository costRepository,
                                          UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.saleRepository = saleRepository;
        this.costRepository = costRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        boolean hasAdminRole = request.isUserInRole("ROLE_ADMIN");
        String userPrincipalEmail = request.getUserPrincipal() == null
                ? null
                : request.getUserPrincipal().getName();

        if (isForModifyResources(requestURI, request.getMethod())
                && isForbidden(requestURI, hasAdminRole, userPrincipalEmail)) {
            response.sendError(403);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isForModifyResources(String uri, String method) {
        if (uri.startsWith("/costs") && method.equals("DELETE")) {
            return true;
        }

        List<String> modifyingURIs = List.of(
                "/pictures/add/",
                "/vehicle/sell/",
                "/costs/add/",
                "/costs/update/");

        return modifyingURIs.stream().anyMatch(uri::startsWith);
    }

    private boolean isForbidden(String requestURI, boolean hasAdminRole, String userPrincipalEmail) {
    //TODO: refactoring
        String[] params = requestURI.split("\\/");
        String vehicleUUID = params[params.length - 1];

        long count = hasAdminRole
                ? vehicleRepository.countByUuidAndSaleIsNull(vehicleUUID)
                : vehicleRepository.countByUuidAndOwner_EmailAndSaleIsNull(vehicleUUID, userPrincipalEmail);

        if (count == 0) {
            return true;
        }

        if (params[0].equals("costs")) {
            long costId = Long.parseLong(params[params.length - 2]);
            return costRepository.findIsCompletedById(costId);
        }

        return false;
    }
}
