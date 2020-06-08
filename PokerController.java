package com.kata.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kata.demo.service.PokerService;

@Controller
public class PokerController {

	@Autowired
	PokerService pokerService;

	@RequestMapping(value = "/whowins")
	@ResponseBody
	public String getWhoWins(@RequestParam("white") String white, @RequestParam("black") String black) {
		return pokerService.processHands(white, black);
	}

}
