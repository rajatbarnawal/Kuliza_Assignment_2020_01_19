package com.example.demo.respository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Father;

@Repository
public interface FatherRepository extends CrudRepository<Father, Long>{
	
	@Query("Select Father from #{#entityName} Father where id = ?1")
	public Father fatherByIdOnly(Long fatherId);
	
	@Query("Select Father from #{#entityName} Father where id = ?1 and fName = ?1")
	public Father fatherById(Long fatherId, String fatherName);
	
	@Query("Select Father from #{#entityName} Father")
	public List<Father> getAllFathers();
	
	@Modifying
	@Transactional
	@Query("Delete #{#entityName} Father where id = ?1")
	public void deleteById(Long deleteId);
	
	

}
