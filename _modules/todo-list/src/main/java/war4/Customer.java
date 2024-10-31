package war4;

import java.time.LocalDate;

public record Customer(String id, String userName, String email, String name, String surname, LocalDate birthDate) {
}
