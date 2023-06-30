package com.makiia.crosscutting.persistence.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.makiia.crosscutting.persistence.entity.EntyRecmaetarivalor;

public interface EntyRecmaetarivalorRepository extends JpaRepository<EntyRecmaetarivalor,Integer>
{
        String FILTER_PRICES_RECUNIKEYRETP_QUERY = "select c from EntyRecmaetarivalor c  where c.recUnikeyRetp  = ?1 and c.recEstregRetp ='1'";
        @Query(value = FILTER_PRICES_RECUNIKEYRETP_QUERY)
        Page<EntyRecmaetarivalor> findByrecUnikeyRetp(Integer filter, Pageable pageable);

      
        String FILTER_PRICE_QUERY = "select c from EntyRecmaetarivalor c where UPPER(c.apjNroregAphp) = UPPER(?1)";
        @Query(value = FILTER_PRICE_QUERY)
        Page<EntyRecmaetarivalor> findNroReg(String filter , Pageable pageable);

}
