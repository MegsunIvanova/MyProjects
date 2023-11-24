package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.service.impl.ModifyingPermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final ModifyingPermissionInterceptor modifyingPermissionInterceptor;

    public WebConfiguration(ModifyingPermissionInterceptor modifyingPermissionInterceptor) {
        this.modifyingPermissionInterceptor = modifyingPermissionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(modifyingPermissionInterceptor);
    }
}
