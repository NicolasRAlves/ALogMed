package com.alogmed.clinica;

//import com.alogmed.clinica.entity.Role;
//import com.alogmed.clinica.entity.Status;
//import com.alogmed.clinica.entity.User;
//import com.alogmed.clinica.service.UserService;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ALogMedClinicaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ALogMedClinicaApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			User user = new User();
//			user.setName("Pedro Santos");
//			user.setEmail("pedro@exemplo.com");
//			user.setCpf("12345678900");
//			user.setRg("RJ123456");
//			user.setRole(Role.RECEPTIONIST);
//			user.setAge(25);
//			user.setWeight(70.0);
//			user.setHeight(1.70);
//			user.setState("RJ");
//			user.setCity("Rio de Janeiro");
//			user.setStatus(Status.ACTIVE);
//			user.setPassword("senha123");
//
//			User savedUser = userService.saveUser(user);
//			System.out.println("Usuário salvo: " + savedUser.getName() + ", ID: " + savedUser.getId());
//		};
//	}
}