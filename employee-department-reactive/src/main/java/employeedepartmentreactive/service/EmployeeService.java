package employeedepartmentreactive.service;

import employeedepartmentreactive.dto.EmployeeDTO;
import employeedepartmentreactive.model.error.CustomErrorException;
import employeedepartmentreactive.model.error.EmployeeNotFoundException;
import employeedepartmentreactive.model.error.ErrorDetails;
import employeedepartmentreactive.model.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService{


    private static final String URL = "https://656f1d956529ec1c62375ec4.mockapi.io/";
    private static final String RESOURCE = "/empleados/empleados";

    private static final WebClient webClient;

    static {
        webClient = WebClient.create(URL);
    }

    public Mono<List<EmployeeDTO>> findAll() {
        return webClient
                .get()
                .uri(RESOURCE)
                .retrieve()
                .bodyToFlux(EmployeeDTO.class)
                .collectList()
                .defaultIfEmpty(Collections.emptyList());
    }

    public Mono<EmployeeDTO> getEmployeeById(Integer id) {
        return webClient.get()
                .uri(RESOURCE + "/{id}", id)
                .retrieve()
                .bodyToMono(EmployeeDTO.class)
                .switchIfEmpty(Mono.error(() -> new EmployeeNotFoundException(id)));
    }

    public Mono<Void> saveEmployee(EmployeeDTO user) {
        return webClient.post()
                .uri(RESOURCE)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateEmployee(Integer id, EmployeeDTO user) {
        return webClient.put()
                .uri(RESOURCE + "/{id}", id)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteEmployee(Integer id) {
        return webClient.delete()
                .uri(RESOURCE + "/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .switchIfEmpty(Mono.error(() -> {
                    var message = String.format("employee not found: %s", id);
                    var errorResponse = ErrorResponse.builder()
                            .traceId(RandomStringUtils.randomAlphanumeric(10))
                            .status(HttpStatus.NOT_FOUND)
                            .timestamp(OffsetDateTime.now())
                            .message(message)
                            .errors(List.of(ErrorDetails.API_EMPLOYEE_NOT_FOUND))
                            .build();
                    throw new CustomErrorException(message, errorResponse);
                }));
    }
}
