package ao.it.chandsoft.world;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WolrdApplication {

    public static void main(String[] args) {
        SpringApplication.run(WolrdApplication.class, args);
    }


    @Bean
    public PhysicalNamingStrategy physical() {
        return new PhysicalNamingStrategyStandardImpl();
    }

    @Bean
    public ImplicitNamingStrategy implicit() {
        return new ImplicitNamingStrategyLegacyJpaImpl();
    }
}
