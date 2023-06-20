package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoriesResponse;
import peaksoft.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.response.CategoriesResponse(c.id, c.name) from Category c")
    List<CategoriesResponse> getAllCategories();

    Page<CategoriesResponse> findAllBy(Pageable pageable);
}
