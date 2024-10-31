package code.service.serialization;

import code.model.exception.MortgageException;
import code.model.rate.Rate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class SerializationService implements SerializationServiceI {

   public static final Path SERIALIZED_PATH = Path.of("src/main/resources/serialized");

   @SneakyThrows
   public SerializationService() {
      if (Files.exists(SERIALIZED_PATH)) {
         Files.writeString(SERIALIZED_PATH, "", StandardOpenOption.TRUNCATE_EXISTING);
      } else {
         Files.createFile(SERIALIZED_PATH);
      }
   }


   @Override
   public void serialize(List<Rate> rates) {
      try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
              new BufferedOutputStream(
                      new FileOutputStream(SERIALIZED_PATH.toFile())))) {
         for (Rate rate : rates) {
            objectOutputStream.writeObject(rate);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
   @Override
   public List<Rate> deserialize(Path serializedPath) {
      List<Rate> rates = new ArrayList<>();
      try (ObjectInputStream objectInputStream = new ObjectInputStream(
              new BufferedInputStream(
                      new FileInputStream(serializedPath.toFile())
              ))) {
         while (true) {
            Object objectRead = objectInputStream.readObject();
            if (!(objectRead instanceof Rate)) {
               throw new MortgageException("Deserialized object is not a Rate");
            }
            rates.add((Rate) objectRead);
         }
      } catch (EOFException e) {
         return rates;
      } catch (IOException | ClassNotFoundException e) {
         throw new RuntimeException(e);
      }
   }
}