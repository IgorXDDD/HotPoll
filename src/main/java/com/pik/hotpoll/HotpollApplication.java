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

	// @GetMapping("/")
	// public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
	// 	return String.format("Hello %s!", name);
	// }

	@GetMapping("/")
	public ModelAndView frontend(ModelMap model) {
		model.addAttribute("attribute", "frontend");
		return new ModelAndView("forward:/frontend/index.html", model);
	}

	@GetMapping("/test")
	public String testString(@RequestParam(value = "text", defaultValue = "test") String text) {
		if(text.equals("OK")){
			System.out.println("Correct test param!");
		}else {
			System.out.println("Incorrect test param!: " + text);
		}
		return String.format("Testing %s!", text);
	}

}