package com.expert.certification_nlw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraController")
public class PrimeiraController {

  @GetMapping("/retornarPrimeiraController")
  public Usuario primeiraController() {
      var usuario = new Usuario("Rafael", 25);
      return usuario;
  }

  @GetMapping("/meuprimeiroPost")
  public String meuPrimeiroPost() {
      return "Primeiro post";
  }


  record Usuario(String nome, int idade){}
}
