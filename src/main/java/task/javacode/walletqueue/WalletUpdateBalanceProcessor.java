package task.javacode.walletqueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import task.javacode.service.WalletService;

@Component
public class WalletUpdateBalanceProcessor {
    private final WalletUpdateBalanceQueue operationQueue;
    private final WalletService walletService;
    @Value("${processor.thread-count:5}")
    private int threadCount;


    public WalletUpdateBalanceProcessor(
            WalletUpdateBalanceQueue operationQueue, WalletService walletService) {
        this.operationQueue = operationQueue;
        this.walletService = walletService;
    }

    public void startProcessing() {
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        walletService.updateBalance(operationQueue.take());
                    } catch (Exception e) {
                        System.err.println("Error processing operation: " + e.getMessage());
                    }
                }
            }, "ProcessorThread-" + i).start();
        }
    }
}

