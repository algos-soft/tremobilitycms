package it.windtre.tremobilitycms.backend.repositories;

import it.windtre.tremobilitycms.backend.data.entity.Zoneitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zoneitem, Long> {

    Page<Zoneitem> findBy(Pageable page);

    Page<Zoneitem> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);

    List<Zoneitem> findAll();

    //no more used: List<Zoneitem> findByServiceitem(Long serviceitem);

    //@Query("SELECT con FROM Contact con  WHERE con.phoneType=(:pType) AND con.lastName= (:lName)")
    //List<Contact> findByLastNameAndPhoneType(@Param("pType") PhoneType pType, @Param("lName") String lName);

    //@Query("SELECT z FROM Zoneitem z WHERE z.serviceitem=(:serviceitemId)")
    //Page<Zoneitem> findByServiceitem(@Param("serviceitemId") Long serviceitemId, @Param("page") Pageable page);

    @Query(value = "SELECT * FROM Zoneitem z WHERE z.serviceitem = :serviceitem",
            countQuery = "SELECT count(*) FROM Zoneitem z WHERE z.serviceitem = :serviceitem",
            nativeQuery = true)
    Page<Zoneitem> findByServiceitem(@Param("serviceitem") Long serviceitem, Pageable page);

    int countByServiceitem(Long serviceitem);

}
