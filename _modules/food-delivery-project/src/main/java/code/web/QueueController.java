package code.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QueueController {

   public static final String QUEUE = "queue";

   @GetMapping(QUEUE)
   public String getQueue() {
      return "queue";
   }
}