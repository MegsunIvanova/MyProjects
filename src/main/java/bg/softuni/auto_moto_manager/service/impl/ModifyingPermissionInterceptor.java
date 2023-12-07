package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.repository.CostRepository;
import bg.softuni.auto_moto_manager.repository.UserRepository;
import bg.softuni.auto_moto_manager.repository.VehicleRepository;
import bg.softuni.auto_moto_manager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class ModifyingPermissionInterceptor implements HandlerInterceptor {
    private final CostRepository costRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final VehicleRepository vehicleRepository;

    public ModifyingPermissionInterceptor(CostRepository costRepository,
                                          UserRepository userRepository,
                                          UserService userService, VehicleRepository vehicleRepository) {
        this.costRepository = costRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

//        String requestURI = request.getRequestURI();
//
//        boolean hasAdminRole = request.isUserInRole("ROLE_ADMIN");
//        String userPrincipalEmail = request.getUserPrincipal() == null
//                ? null
//                : request.getUserPrincipal().getName();
//
//        if (isForModifyResources(requestURI, request.getMethod())
//                && isForbidden(requestURI, hasAdminRole, userPrincipalEmail)) {
//            response.sendError(403);
//            return false;
//        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

//    private boolean isForModifyResources(String uri, String method) {
//        if (uri.startsWith("/costs") && method.equals("DELETE")) {
//            return true;
//        }
//
//        List<String> modifyingURIs = List.of(
//                "/pictures/add/",
//                "/vehicle/sell/",
//                "/costs/add/",
//                "/costs/update/");
//
//        return modifyingURIs.stream().anyMatch(uri::startsWith);
//    }

//    private boolean isForbidden(String requestURI, boolean hasAdminRole, String userPrincipalEmail) {
//        String[] params = requestURI.split("\\/");
//        String vehicleUUID = params[params.length - 1];
//
//        if (!userService.hasPermissionToModify(vehicleUUID)) {
//            return true;
//        } else if (params[0].equals("costs")) {
//            long costId = Long.parseLong(params[params.length - 2]);
//            return costRepository.findIsCompletedById(costId);
//        } else {
//            return false;
//        }
//    }
}
