package code.business;

import code.business.dao.CatDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatService {

    private final CatDAO catDAO;

}