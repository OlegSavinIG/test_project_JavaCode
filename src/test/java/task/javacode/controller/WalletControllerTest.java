package task.javacode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import task.javacode.model.OperationType;
import task.javacode.model.WalletBalanceUpdateRequest;
import task.javacode.model.WalletResponse;
import task.javacode.service.WalletService;
import task.javacode.walletqueue.WalletUpdateBalanceQueue;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private WalletUpdateBalanceQueue walletQueue;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBalance_ShouldReturnWalletResponse() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletResponse mockResponse = WalletResponse.builder()
                .id(walletId)
                .balance(BigDecimal.valueOf(1000))
                .build();

        Mockito.when(walletService.getBalance(eq(walletId))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/wallet/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void updateBalance_ShouldReturnAccepted() throws Exception {
        WalletBalanceUpdateRequest request = WalletBalanceUpdateRequest.builder()
                .id(UUID.randomUUID())
                .operationType(OperationType.DEPOSIT)
                .amount(BigDecimal.valueOf(500))
                .build();

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Update request has been queued for processing."));

        Mockito.verify(walletQueue).add(any(WalletBalanceUpdateRequest.class));
    }
}
