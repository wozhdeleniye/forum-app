package ru.tsu.hits.forum.core.Category.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.forum.core.Category.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {
    List<CategoryEntity> findAllByParentCategoryOrderByName(String id);

    @Query("select c from CategoryEntity c where c.name LIKE %:name%")
    List<CategoryEntity> findAllByNameIgnoreCase(@Param("name") String name);
}
