package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.repository.SaleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class ModifyingInterceptor implements HandlerInterceptor {
    private final SaleRepository saleRepository;

    public ModifyingInterceptor(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        if (isForModifyResources(requestURI, request.getMethod())) {
            String[] params = requestURI.split("\\/");
            String vehicleUUID = params[params.length - 1];

            boolean forbidden = saleRepository.findByVehicle_Uuid(vehicleUUID).isPresent();

            if (forbidden) {
                response.sendError(403);
            }

            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isForModifyResources(String uri, String method) {
        if (uri.startsWith("/pictures/add/")) {
            return true;
        }

        if (uri.startsWith("/vehicle/sell/")) {
            return true;
        }
        if (uri.startsWith("/costs/add/")) {
            return true;
        }

        if (uri.startsWith("/costs/update/")) {
            return true;
        }

        if (uri.startsWith("/costs") && method.equals("DELETE")) {
            return true;
        }

        return false;
    }
}
