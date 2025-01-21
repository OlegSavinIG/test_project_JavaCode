package task.javacode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import task.javacode.exception.InsufficientBalanceException;
import task.javacode.exception.WalletNotExistException;
import task.javacode.model.WalletBalanceUpdateRequest;
import task.javacode.model.WalletEntity;
import task.javacode.model.WalletResponse;
import task.javacode.model.mapper.WalletMapper;
import task.javacode.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public WalletResponse getBalance(UUID walletId) {
        WalletEntity walletEntity = walletRepository.findById(walletId).orElseThrow(
                () -> new WalletNotExistException("Wallet not exist"));
        return WalletMapper.toResponse(walletEntity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public WalletResponse updateBalance(WalletBalanceUpdateRequest request) {
        WalletEntity currentEntity = walletRepository.findByIdForUpdate(request.getId()).orElseThrow(
                () -> new WalletNotExistException("Wallet not exist"));
        WalletEntity newEntity = updateWalletBalance(request, currentEntity);
        walletRepository.save(newEntity);
        return WalletMapper.toResponse(newEntity);
    }

    private WalletEntity updateWalletBalance(WalletBalanceUpdateRequest request, WalletEntity currentEntity) {
        switch (request.getOperationType()) {
            case WITHDRAW -> {
                BigDecimal newBalanceWithdraw = currentEntity.getBalance().subtract(request.getAmount());
                if (newBalanceWithdraw.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientBalanceException("Not enough money");
                }
                currentEntity.setBalance(newBalanceWithdraw);
            }
            case DEPOSIT -> {
                BigDecimal newBalanceDeposit = currentEntity.getBalance().add(request.getAmount());
                currentEntity.setBalance(newBalanceDeposit);
            }
            default -> throw new IllegalArgumentException("Unsupported operation type: " + request.getOperationType());
        }
        return currentEntity;
    }
}
