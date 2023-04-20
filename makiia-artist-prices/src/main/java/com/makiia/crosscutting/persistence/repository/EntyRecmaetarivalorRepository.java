package com.makiia.crosscutting.persistence.repository;
import com.makiia.crosscutting.persistence.entity.EntyRecmaetarivalor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EntyRecmaetarivalorRepository extends JpaRepository<EntyRecmaetarivalor,Integer>
{
        String FILTER_PRICE_QUERY = "select c from EntyRecmaetarivalor c where UPPER(c.apjNroregAphp) = UPPER(?1)";
        @Query(value = FILTER_PRICE_QUERY)
        Page<EntyRecmaetarivalor> findNroReg(String filter , Pageable pageable);

}