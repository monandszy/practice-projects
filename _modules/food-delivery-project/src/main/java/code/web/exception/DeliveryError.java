package code.web.exception;

public class DeliveryError extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public DeliveryError() {
   }

   public DeliveryError(String message) {
      super(message);
   }
}