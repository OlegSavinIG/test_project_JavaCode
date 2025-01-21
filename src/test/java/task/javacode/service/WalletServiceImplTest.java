package task.javacode.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import task.javacode.exception.InsufficientBalanceException;
import task.javacode.exception.WalletNotExistException;
import task.javacode.model.OperationType;
import task.javacode.model.WalletBalanceUpdateRequest;
import task.javacode.model.WalletEntity;
import task.javacode.model.WalletResponse;
import task.javacode.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private UUID walletId;
    private WalletEntity walletEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        walletId = UUID.randomUUID();
        walletEntity = WalletEntity.builder()
                .id(walletId)
                .balance(BigDecimal.valueOf(1000))
                .build();
    }

    @Test
    void getBalance_ShouldReturnWalletResponse() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(walletEntity));

        WalletResponse response = walletService.getBalance(walletId);

        assertNotNull(response);
        assertEquals(walletId, response.getId());
        assertEquals(BigDecimal.valueOf(1000), response.getBalance());
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void getBalance_ShouldThrowWalletNotExistException() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(WalletNotExistException.class, () -> walletService.getBalance(walletId));
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void updateBalance_ShouldDepositAmount() {
        WalletBalanceUpdateRequest request = WalletBalanceUpdateRequest.builder()
                .id(walletId)
                .operationType(OperationType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(walletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        WalletResponse response = walletService.updateBalance(request);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(1500), walletEntity.getBalance());
        verify(walletRepository, times(1)).findByIdForUpdate(walletId);
        verify(walletRepository, times(1)).save(walletEntity);
    }

    @Test
    void updateBalance_ShouldWithdrawAmount() {
        WalletBalanceUpdateRequest request = WalletBalanceUpdateRequest.builder()
                .id(walletId)
                .operationType(OperationType.WITHDRAW)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(walletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        WalletResponse response = walletService.updateBalance(request);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(500), walletEntity.getBalance());
        verify(walletRepository, times(1)).findByIdForUpdate(walletId);
        verify(walletRepository, times(1)).save(walletEntity);
    }

    @Test
    void updateBalance_ShouldThrowInsufficientBalanceException() {
        WalletBalanceUpdateRequest request = WalletBalanceUpdateRequest.builder()
                .id(walletId)
                .operationType(OperationType.WITHDRAW)
                .amount(BigDecimal.valueOf(1500))
                .build();

        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.of(walletEntity));

        assertThrows(InsufficientBalanceException.class, () -> walletService.updateBalance(request));
        verify(walletRepository, times(1)).findByIdForUpdate(walletId);
        verify(walletRepository, never()).save(any(WalletEntity.class));
    }

    @Test
    void updateBalance_ShouldThrowWalletNotExistException() {
        WalletBalanceUpdateRequest request = WalletBalanceUpdateRequest.builder()
                .id(walletId)
                .operationType(OperationType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .build();

        when(walletRepository.findByIdForUpdate(walletId)).thenReturn(Optional.empty());

        assertThrows(WalletNotExistException.class, () -> walletService.updateBalance(request));
        verify(walletRepository, times(1)).findByIdForUpdate(walletId);
    }
}
