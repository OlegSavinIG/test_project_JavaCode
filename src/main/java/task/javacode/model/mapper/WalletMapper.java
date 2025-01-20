package task.javacode.model.mapper;

import task.javacode.model.WalletEntity;
import task.javacode.model.WalletResponse;

public class WalletMapper {
    public static WalletResponse toResponse(WalletEntity entity) {
        return WalletResponse.builder()
                .id(entity.getId())
                .balance(entity.getBalance())
                .build();
    }
}
