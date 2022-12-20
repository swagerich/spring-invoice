package com.erich.factura.Controllers.Api;

import com.erich.factura.Entity.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Tag(name = "Client")
public interface ApiClient {

    @ResponseBody
    @GetMapping(value = "/listar-rest",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Clients",description = "client list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description ="The item list / An empty list")
    })
    List<Client> finAll();

}
