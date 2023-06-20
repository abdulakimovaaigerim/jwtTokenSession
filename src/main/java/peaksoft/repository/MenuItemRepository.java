package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entities.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItemResponse> getMenuItemById(Long menuItemId);

    @Query("select distinct new peaksoft.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from MenuItem m left join StopList s on s.menuItem.id = m.id where s.id is null or s.date <> current_date ")
    List<MenuItemResponse> getAllMenuItems();

    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            " from MenuItem m where m.name ilike concat('%', :word, '%') or m.description ilike concat('%', :word, '%')" +
            " or m.subCategory.name ilike concat('%', :word, '%') or m.subCategory.category.name ilike concat('%', :word, '%')")
    List<MenuItemResponse> globalSearch(String word);

    List<MenuItemResponse> findMenuItemByIsVegetarian(boolean isTrue);

    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where  m.InStock=true order by m.price ")
    List<MenuItemResponse> sortByPriceAsc();

    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where  m.InStock=true order by m.price desc ")
    List<MenuItemResponse> sortByPriceDesc();

    @Query("select new peaksoft.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from MenuItem m")
    Page<MenuItemResponse> pagination(Pageable pageable);
}
