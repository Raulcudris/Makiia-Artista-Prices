package com.makiia.modules.prices.contracts;
import java.util.List;

import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorResponse;
import com.makiia.crosscutting.exceptions.Main.EBusinessException;

public interface  IjpaDataProviders<T> {

    EntyRecmaetarivalorResponse getAll() throws EBusinessException;

    EntyRecmaetarivalorResponse getAll (int currentPage , int PageSize,String parameter ,String filter) throws EBusinessException;

    T get(Integer id) throws EBusinessException;

    T save(T dto) throws EBusinessException;

    List<T> save(List<T> dto) throws EBusinessException;

    T update(Integer id, T dto) throws EBusinessException;

    void delete(Integer id) throws EBusinessException;
}
