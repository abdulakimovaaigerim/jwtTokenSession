package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemForCheque;
import peaksoft.entities.Cheque;

import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    Page<ChequeResponse> getAllChequeByUserId(Long userId, PageRequest pageRequest);

    @Query("select new peaksoft.dto.response.ChequeResponse(c.id, c.createAt," +
            " c.user.firstName, sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.id = ?1 group by c.id, c.createAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    Optional<ChequeResponse> getChequeById(Long chequeId);

    @Query("select new peaksoft.dto.response.MenuItemForCheque(m.id, m.name, m.price, count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemForCheque> getMenuItems(Long chequeId);
}
