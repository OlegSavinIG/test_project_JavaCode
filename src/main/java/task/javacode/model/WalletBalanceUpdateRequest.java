package task.javacode.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class WalletBalanceUpdateRequest {
    @NotNull
    private UUID id;
    @NotNull
    private OperationType operationType;
    @NotNull
    @Positive
    private BigDecimal amount;
}
