package sirdarey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;
import sirdarey.config.RedisHashComponent;
import sirdarey.dto.ApiKey;
import sirdarey.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SECURITY {


    @Autowired
    private RedisHashComponent redisHashComponent;

    @PostConstruct
    public void initKeysToRedis() {
        List<ApiKey> apiKeys = new ArrayList<>();
        
        apiKeys.add(new ApiKey("US48-EF0C-427E-8CCF", Stream.of(AppConstants.USER_SERVICE_KEY)
                .collect(Collectors.toList())));
        
        apiKeys.add(new ApiKey("GR48-EF0C-489E-8C2U", Stream.of(AppConstants.GADMIN_SERVICE_KEY)
                .collect(Collectors.toList())));
        
        apiKeys.add(new ApiKey("BG48-EF0C-489E-8CC0", Stream.of(AppConstants.BADMIN_SERVICE_KEY,
        		AppConstants.GADMIN_SERVICE_KEY).collect(Collectors.toList())));
        
        List<Object> lists = redisHashComponent.hValues(AppConstants.RECORD_KEY);
        if (lists.isEmpty()) {
            apiKeys.forEach(k -> redisHashComponent.hSet(AppConstants.RECORD_KEY, k.getKey(), k));
        }
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(AppConstants.USER_SERVICE_KEY,
						r -> r.path("/api/user-service/**")
                        .filters(f -> f.stripPrefix(2)).uri("http://localhost:8001"))
                .route(AppConstants.GADMIN_SERVICE_KEY,
						r -> r.path("/api/gadmin-service/**")
                        .filters(f -> f.stripPrefix(2)).uri("http://localhost:8001"))
                .route(AppConstants.BADMIN_SERVICE_KEY,
						r -> r.path("/api/badmin-service/**")
                        .filters(f -> f.stripPrefix(2)).uri("http://localhost:8002"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SECURITY.class, args);
    }

}

