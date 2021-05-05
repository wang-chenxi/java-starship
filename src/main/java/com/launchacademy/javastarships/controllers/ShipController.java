package com.launchacademy.javastarships.controllers;

import com.launchacademy.javastarships.models.StarShip;
import com.launchacademy.javastarships.services.StarShipService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/starships")
public class ShipController {

  @Autowired
  StarShipService starShipService;

  public ShipController(StarShipService starShipService) {
    this.starShipService = starShipService;
  }


  @GetMapping
  public String listStarShips(Model model) {
    List starships = starShipService.findAll();
    model.addAttribute("ships", starships);
    return "starShips/index";
  }

  @GetMapping("/{id}")
  public String showShip(@PathVariable int id, Model model) {
    List starships = starShipService.findAll();
    if(id>starships.size() || id <=0 ){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }else{
    Object ship;
    ship = starships.get(id-1);
    System.out.println(ship);
    model.addAttribute("ship", ship);}
    return "starShips/show";
  }

  @GetMapping("/new")
  public String getNewForm(@ModelAttribute StarShip starShip) {
    return "starships/new";
  }

  @PostMapping
  public String addShip(@ModelAttribute StarShip starShip) {
    starShip.setId(starShipService.findAll().size() + 1);
    starShipService.addToList(starShip);
    return "redirect:/starships/" + starShip.getId();
  }

}