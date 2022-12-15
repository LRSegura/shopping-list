package com.lab.shoppinglist.repository;

import com.lab.shoppinglist.model.list.DetailList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailListRepository extends CrudRepository<DetailList,Long> {
}
