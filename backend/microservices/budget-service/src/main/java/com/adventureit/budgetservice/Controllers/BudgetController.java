package com.adventureit.budgetservice.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/budget")
public class BudgetController {
	@GetMapping("/test")
	String test(){
		return "Budget Controller is functioning";
	}
}
