package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.service.impl.ModifyingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final ModifyingInterceptor modifyingInterceptor;

    public WebConfiguration(ModifyingInterceptor modifyingInterceptor) {
        this.modifyingInterceptor = modifyingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(modifyingInterceptor);
    }
}
