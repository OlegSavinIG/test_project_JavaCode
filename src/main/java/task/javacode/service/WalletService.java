package task.javacode.service;

import task.javacode.model.OperationType;
import task.javacode.model.WalletBalanceUpdateRequest;
import task.javacode.model.WalletResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {
    WalletResponse getBalance(UUID walletId);

    WalletResponse updateBalance(WalletBalanceUpdateRequest request);
}
