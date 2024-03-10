package com.omid.employeeservice.chef;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef, String> {
    Chef getChefById(String id);

    @Query("from Chef c where c.firstName like :value or c.lastName like :value or c.nationalCode like :value")
    List<Chef> search(@Param("value") String value);
}
