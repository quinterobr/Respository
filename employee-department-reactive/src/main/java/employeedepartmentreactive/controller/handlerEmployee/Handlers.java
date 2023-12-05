package employeedepartmentreactive.controller.handlerEmployee;

import employeedepartmentreactive.dto.EmployeeDTO;
import employeedepartmentreactive.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Controller
public class Handlers {

    private final EmployeeService employeeService;

    public Mono<ServerResponse> getAllEmployees() {
        return ServerResponse.ok()
                .body(employeeService.findAll(), EmployeeDTO.class);
    }

    public Mono<ServerResponse> getEmployeeById(ServerRequest request) {
        int employeeId = Integer.parseInt(request.pathVariable("id"));
        return employeeService.getEmployeeById(employeeId)
                .flatMap(employeeDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(employeeDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveEmployee(ServerRequest request) {
        return request.bodyToMono(EmployeeDTO.class)
                .flatMap(employeeService::saveEmployee)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(EmployeeDTO.class)
                .flatMap(user -> employeeService.updateEmployee(userId, user))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        int employeeId = Integer.parseInt(request.pathVariable("id"));
        return employeeService.deleteEmployee(employeeId)
                .then(ServerResponse.ok().build());
    }
}
