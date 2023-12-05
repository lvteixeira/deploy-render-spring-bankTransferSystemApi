package com.acme.bankTransferSystemApi.controller;

import com.acme.bankTransferSystemApi.dto.TransferDTO;
import com.acme.bankTransferSystemApi.model.TransferEntity;
import com.acme.bankTransferSystemApi.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("statement")
public class StatementController {
    @Autowired
    private TransferService transferService;

    public StatementController(TransferService transferService) {
        this.transferService = transferService;
    }


    @Operation(summary="Get bank statements by userId", description="Returns an array of transfer objects or nothing incase there's none.")
    @ApiResponse(responseCode="200",description="success")
    @GetMapping("{userId}")
    public ResponseEntity<List<TransferDTO>> getStatementsByUserId(@PathVariable("userId") String userId) {
        List<TransferEntity> statements = transferService.findByUserId(userId);
        List<TransferDTO> dtoStatements = transferService.convertEntityListToDTOList(statements);
        return ResponseEntity.status(200).body(dtoStatements);
    }
}
