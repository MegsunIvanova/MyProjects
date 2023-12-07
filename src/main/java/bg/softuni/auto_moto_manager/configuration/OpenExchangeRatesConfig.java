package bg.softuni.auto_moto_manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "open.exchange.rates")
public class OpenExchangeRatesConfig {

    private String appId;
    private List<String> symbols;
    private String host;
    private String schema;
    private String path;
    private boolean enabled;

    public String getAppId() {
        return appId;
    }

    public OpenExchangeRatesConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public OpenExchangeRatesConfig setSymbols(List<String> symbols) {
        this.symbols = symbols;
        return this;
    }

    public String getHost() {
        return host;
    }

    public OpenExchangeRatesConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public OpenExchangeRatesConfig setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getPath() {
        return path;
    }

    public OpenExchangeRatesConfig setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public OpenExchangeRatesConfig setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String urlTemplate () {
        return new StringBuilder()
                .append(schema)
                .append("://")
                .append(host)
                .append(path)
                .append("?app_id={app_id}&symbols={symbols}")
                .toString();
    }

    public Map<String,String> requestParams () {
        return Map.of(
                "app_id", appId,
                "symbols", symbols.stream().collect(Collectors.joining(","))
        );
    }
}
