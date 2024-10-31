package code.service.serialization;

import code.model.rate.Rate;

import java.nio.file.Path;
import java.util.List;

public interface SerializationServiceI {
   void serialize(List<Rate> rates);

   List<Rate> deserialize(Path serializedPath);
}