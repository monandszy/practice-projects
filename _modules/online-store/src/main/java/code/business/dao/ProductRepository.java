package code.business.dao;

import code.domain.Product;

import java.util.List;

public interface ProductRepository extends DAO<Product> {
  List<Product> getQuestionableProducts();

  void deleteWhereIdIn(List<Integer> ids);
}