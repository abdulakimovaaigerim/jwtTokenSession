package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entities.StopList;

import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {

    @Query("select new peaksoft.dto.response.StopListResponse(s.id, s.reason, s.date) from StopList s where s.menuItem.id=:menuItemId")
    List<StopListResponse> findAllByMenuItemId(@Param("menuItemId") Long menuItemId);
    Optional<StopListResponse> getStopListById(Long stopListId);

}
