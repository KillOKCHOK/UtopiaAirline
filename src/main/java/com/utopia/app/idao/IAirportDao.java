package com.utopia.app.idao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.utopia.app.model.Airport;

public interface IAirportDao extends JpaRepository<Airport,Long> {

	@Query("select b from tbl_book_loans b where b.id.book.id = ?1 and b.id.libraryBranch.id = ?2 and b.id.borrower.id = ?3")
	List<Airport> getAirportListByName(String name);

}
