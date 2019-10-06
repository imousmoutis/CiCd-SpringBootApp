package gr.ioannis.cicd;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(App.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
