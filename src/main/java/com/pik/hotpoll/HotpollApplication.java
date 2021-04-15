package com.pik.hotpoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class HotpollApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotpollApplication.class, args);
	}

}