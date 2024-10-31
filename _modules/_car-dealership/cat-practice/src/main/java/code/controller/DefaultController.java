package code.controller;

import code.infrastructure.database.entity.CatEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
@AllArgsConstructor
public class DefaultController {
   @GetMapping
   public String employees() {
      return "redirect:/cats";
   }
}