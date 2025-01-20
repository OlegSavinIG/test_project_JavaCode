package task.javacode.walletqueue;

import task.javacode.model.WalletBalanceUpdateRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WalletUpdateBalanceQueue {
        private final BlockingQueue<WalletBalanceUpdateRequest> queue = new LinkedBlockingQueue<>();

        public void add(WalletBalanceUpdateRequest request) {
            try {
                queue.put(request);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Failed to enqueue operation", e);
            }
        }

        public WalletBalanceUpdateRequest take() throws InterruptedException {
            return queue.take();
        }
    }

