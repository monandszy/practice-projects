package code.web;

import code.web.exception.DeliveryError;
import code.web.exception.NotImplementedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

   @ExceptionHandler(Throwable.class)
   public ModelAndView handleException(Exception ex) {
      String message = String.format("Exception [%s] occurred: [%s]", ex.getClass(), ex.getMessage());
      log.error(message, ex);
      ModelAndView modelAndView = new ModelAndView("error");
      modelAndView.addObject("message", message);
      return modelAndView;
   }

   @ExceptionHandler(DeliveryError.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ModelAndView handleException(DeliveryError ex) {
      String message = String.format("DeliveryError occurred: [%s]", ex.getMessage());
      log.error(message, ex);
      ModelAndView modelAndView = new ModelAndView("error");
      modelAndView.addObject("message", message);
      return modelAndView;
   }


   @ExceptionHandler(NotImplementedException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ModelAndView handleException(NotImplementedException ex) {
      String message = String.format("NotImplementedException occurred: [%s]", ex.getMessage());
      log.error(message, ex);
      ModelAndView modelAndView = new ModelAndView("error");
      modelAndView.addObject("message", message);
      return modelAndView;
   }

   @ExceptionHandler(BindException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ModelAndView handleBindException(BindException ex) {
      String message = String.format("Bad request for field: [%s], wrong value: [%s]",
          Optional.ofNullable(ex.getFieldError()).map(FieldError::getField).orElse(null),
          Optional.ofNullable(ex.getFieldError()).map(FieldError::getRejectedValue).orElse(null));
      log.error(message, ex);
      ModelAndView modelAndView = new ModelAndView("error");
      modelAndView.addObject("message", message);
      return modelAndView;
   }

}