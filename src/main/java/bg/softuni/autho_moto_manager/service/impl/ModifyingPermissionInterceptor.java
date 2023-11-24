package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.SaleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Configuration
public class ModifyingPermissionInterceptor implements HandlerInterceptor {
    private final SaleRepository saleRepository;
    private final CostRepository costRepository;

    public ModifyingPermissionInterceptor(SaleRepository saleRepository, CostRepository costRepository) {
        this.saleRepository = saleRepository;
        this.costRepository = costRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        if (isForModifyResources(requestURI, request.getMethod())
                && isForbidden(requestURI)) {
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

    private boolean isForbidden(String requestURI) {
        String[] params = requestURI.split("\\/");
        String vehicleUUID = params[params.length - 1];
        boolean isSold = saleRepository.findByVehicle_Uuid(vehicleUUID).isPresent();

        if (isSold) {
            return true;
        }

        if (params[0].equals("costs")) {
            long costId = Long.parseLong(params[params.length - 2]);
            return costRepository.findIsCompletedById(costId);
        }

        return false;
    }
}
