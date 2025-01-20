package task.javacode.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.javacode.model.WalletBalanceUpdateRequest;
import task.javacode.model.WalletResponse;
import task.javacode.service.WalletService;
import task.javacode.walletqueue.WalletUpdateBalanceQueue;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
@Validated
public class WalletController {

    private final WalletService walletService;
    private final WalletUpdateBalanceQueue walletQueue;

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getBalance(@NonNull @PathVariable UUID walletId) {
        WalletResponse response = walletService.getBalance(walletId);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<String> updateBalance(@RequestBody WalletBalanceUpdateRequest request) {
        walletQueue.add(request);
        return ResponseEntity.accepted().body("Update request has been queued for processing.");
    }
}
