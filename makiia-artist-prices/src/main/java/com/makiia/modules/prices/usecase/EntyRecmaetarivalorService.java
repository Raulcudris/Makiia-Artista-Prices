package com.makiia.modules.prices.usecase;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.makiia.crosscutting.domain.model.EntyDeleteDto;
import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorDto;
import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorResponse;
import com.makiia.crosscutting.exceptions.ExceptionBuilder;
import com.makiia.crosscutting.exceptions.Main.EBusinessException;
import com.makiia.crosscutting.messages.SearchMessages;
import com.makiia.modules.bus.services.UseCase;
import com.makiia.modules.bus.services.UsecaseServices;
import com.makiia.modules.prices.dataproviders.jpa.JpaEntyRecmaetarivalorDataProviders;


@UseCase
@Service
public class EntyRecmaetarivalorService extends UsecaseServices<EntyRecmaetarivalorDto, JpaEntyRecmaetarivalorDataProviders> {
    @Autowired
    private JpaEntyRecmaetarivalorDataProviders jpaDataProviders;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

     private String localYear;
     private int year;

    public EntyRecmaetarivalorResponse saveBefore(EntyRecmaetarivalorResponse dto) throws EBusinessException {
        try {
            //EntyRecmaetarivalorDto

            List<EntyRecmaetarivalorDto> dtoAux = this.ijpaDataProvider.save(dto.getRspData());
              localYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
               year = Integer.parseInt(localYear);

            // Generar llave auxilar rec_secreg_retp  -> Codigo secuencial unico de la tarifa o precio venta de un item del HomePage
            for (EntyRecmaetarivalorDto dtox : dtoAux) {
                if (dtox.getRecSecregRetp().equals("NA")) {
                    // concatenar
                    dtox.setRecSecregRetp(year+"" + dtox.getRecUnikeyRetp());
                }
            }
            dto.setRspValue("OK");
            dto.setRspMessage("OK");             
            dto.setRspParentKey("NA");             
            dto.setRspAppKey("NA");
            dtoAux = this.ijpaDataProvider.save(dtoAux);
            dto.setRspData(dtoAux);

            return dto;

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public EntyRecmaetarivalorResponse updateAll(EntyRecmaetarivalorResponse dto) throws EBusinessException {
        try {
            //EntyRecmaetarivalorDto

            List<EntyRecmaetarivalorDto> dtoAux = dto.getRspData();

            // Generar llave auxilar rec_secreg_retp  -> Codigo secuencial unico de la tarifa o precio venta de un item del HomePage
            for (EntyRecmaetarivalorDto dtox : dtoAux) {
                dtox = this.ijpaDataProvider.update(dtox.getRecUnikeyRetp(),dtox);
            }
            dto.setRspValue("OK");
            dto.setRspMessage("OK");             
            dto.setRspParentKey("NA");             
            dto.setRspAppKey("NA");
            dto.setRspData(dtoAux);

            return dto;

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public String deleteAll(List<EntyDeleteDto> dto) throws EBusinessException {
        try {

            for (EntyDeleteDto dtox : dto) {
                this.ijpaDataProvider.delete(dtox.getRecPKey());
            }
            return "OK";

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.DELETE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.DELETE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }
}

