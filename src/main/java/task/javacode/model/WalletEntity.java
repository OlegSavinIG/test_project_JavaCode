package task.javacode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "wallets")
@Builder
public class WalletEntity {
    @Id
    private UUID id;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
