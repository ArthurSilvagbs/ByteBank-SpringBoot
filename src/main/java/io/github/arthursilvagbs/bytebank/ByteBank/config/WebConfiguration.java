package io.github.arthursilvagbs.bytebank.ByteBank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("login");
      registry.addViewController("/cadastro").setViewName("cadastro");
      registry.addViewController("/bem-vindo").setViewName("bem-vindo");
      registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
   }
}
