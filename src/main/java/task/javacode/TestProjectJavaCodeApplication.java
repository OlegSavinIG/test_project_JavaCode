package task.javacode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task.javacode.walletqueue.WalletUpdateBalanceProcessor;

@SpringBootApplication
public class TestProjectJavaCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestProjectJavaCodeApplication.class, args);

//        WalletUpdateBalanceProcessor processor = context.getBean(WalletUpdateBalanceProcessor.class);
//        processor.startProcessing();
    }
}
