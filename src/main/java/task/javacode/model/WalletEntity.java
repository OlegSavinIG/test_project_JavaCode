package task.javacode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "wallets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
