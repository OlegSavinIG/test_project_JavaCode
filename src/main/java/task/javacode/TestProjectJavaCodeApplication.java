package task.javacode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestProjectJavaCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestProjectJavaCodeApplication.class, args);
    }
}
