package ru.tsu.hits.forum.Category.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Category.entity.CategoryEntity;


import java.util.List;

@Repository
@Component
public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {
    List<CategoryEntity> findAllByParentCategoryOrderByName(String id);

    @Query("select c from CategoryEntity c where c.name LIKE %:name%")
    List<CategoryEntity> findAllByNameIgnoreCase(@Param("name") String name);
}
