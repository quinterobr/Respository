package employeedepartmentreactive.controller;

import employeedepartmentreactive.controller.handlerEmployee.Handlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EmployeeRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(Handlers handler) {
        return RouterFunctions.route()
                .path("empleados/empleados", builder -> builder
                        .GET("", request -> handler.getAllEmployees())
                        .GET("/{id}", handler::getEmployeeById)
                        .POST("", handler::saveEmployee)
                        .PUT("/{id}", handler::updateEmployee)
                        .DELETE("/{id}", handler::deleteEmployee))
                .build();
    }
}
