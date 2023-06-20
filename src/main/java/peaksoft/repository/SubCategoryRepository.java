package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entities.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("select new peaksoft.dto.response.SubCategoryResponses(s.id, s.name) from SubCategory s where s.category.id = ?1 order by s.name")
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    Optional<SubCategoryResponse> getSubcategoryById(Long subCategoryId);

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.category.name,s.id,s.name) from SubCategory s")
    List<SubCategoryResponse> getAllSb();

}
