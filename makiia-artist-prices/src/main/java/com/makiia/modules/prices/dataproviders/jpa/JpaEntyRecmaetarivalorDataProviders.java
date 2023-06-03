package com.makiia.modules.prices.dataproviders.jpa;
import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorDto;
import com.makiia.crosscutting.domain.model.EntyRecmaetarivalorResponse;
import com.makiia.crosscutting.domain.model.PaginationResponse;
import com.makiia.crosscutting.exceptions.DataProvider;
import com.makiia.crosscutting.exceptions.ExceptionBuilder;
import com.makiia.crosscutting.exceptions.Main.EBusinessException;
import com.makiia.crosscutting.messages.SearchMessages;
import com.makiia.crosscutting.patterns.Translator;
import com.makiia.crosscutting.persistence.entity.EntyRecmaetarivalor;
import com.makiia.crosscutting.persistence.repository.EntyRecmaetarivalorRepository;
import com.makiia.modules.prices.dataproviders.IjpaEntyRecmaetarivalorDataProviders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@DataProvider
public class JpaEntyRecmaetarivalorDataProviders implements IjpaEntyRecmaetarivalorDataProviders {
    @Autowired
    private EntyRecmaetarivalorRepository repository;
    @Autowired
    @Qualifier("entyRecmaetarivalorSaveResponseTranslate")
    private Translator<EntyRecmaetarivalor, EntyRecmaetarivalorDto> saveResponseTranslate;
    @Autowired
    @Qualifier("entyRecmaetarivalorDtoToEntityTranslate")
    private Translator<EntyRecmaetarivalorDto, EntyRecmaetarivalor> dtoToEntityTranslate;


    @Override
    public EntyRecmaetarivalorResponse getAll() throws EBusinessException {
        try {
            List<EntyRecmaetarivalor> responses = (List<EntyRecmaetarivalor>) repository.findAll();
            int currentPage=0;
            int totalPageSize=responses.size();
            Pageable pageable = PageRequest.of(currentPage, totalPageSize);
            //Pageable paginacion
            Page<EntyRecmaetarivalor> ResponsePage = null;
            ResponsePage = repository.findAll(pageable);

            List<EntyRecmaetarivalor> ListPage = ResponsePage.getContent();
            List<EntyRecmaetarivalorDto> content  = ListPage.stream().map(p ->mapToDto(p)).collect(Collectors.toList());

            EntyRecmaetarivalorResponse response = new EntyRecmaetarivalorResponse();
            response.setRspMessage(response.getRspMessage());
            response.setRspValue(response.getRspValue());

            currentPage = currentPage + 1;
            String nextPageUrl = "LocalHost";
            String previousPageUrl = "LocalHost";
            response.setRspPagination(headResponse(currentPage, totalPageSize, ResponsePage.getTotalElements(), ResponsePage.getTotalPages(), ResponsePage.hasNext(), ResponsePage.hasPrevious(), nextPageUrl, previousPageUrl));
            response.setRspData(content);
            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.SEARCH_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.SEARCH_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }


    @Override
    public EntyRecmaetarivalorResponse getAll(int currentPage ,int totalPageSize,String filter) throws EBusinessException {
        try {
            currentPage = currentPage -1;
            Pageable pageable = PageRequest.of(currentPage, totalPageSize);
            Page<EntyRecmaetarivalor> ResponsePage = repository.findNroReg(filter,pageable);
            List<EntyRecmaetarivalor> ListPage = ResponsePage.getContent();
            List<EntyRecmaetarivalorDto> content  = ListPage.stream().map(p ->mapToDto(p)).collect(Collectors.toList());

            EntyRecmaetarivalorResponse response = new EntyRecmaetarivalorResponse();
            response.setRspMessage(response.getRspMessage());
            response.setRspValue(response.getRspValue());

            currentPage = currentPage+1;
            String nextPageUrl ="LocalHost";
            String previousPageUrl = "LocalHost";
            response.setRspPagination(headResponse(currentPage, totalPageSize , ResponsePage.getTotalElements(),ResponsePage.getTotalPages() , ResponsePage.hasNext(), ResponsePage.hasPrevious(),nextPageUrl,previousPageUrl));
            response.setRspData(content);
            return response;


        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.SEARCH_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.SEARCH_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public EntyRecmaetarivalorDto get(Integer id) throws EBusinessException {
        try {
            return saveResponseTranslate.translate(repository.findById(id).get());
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.SEARCH_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.SEARCH_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public EntyRecmaetarivalorDto save(EntyRecmaetarivalorDto dto) throws EBusinessException {
        try {
            return saveResponseTranslate.translate(repository.save(dtoToEntityTranslate.translate(dto)));
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public List<EntyRecmaetarivalorDto> save(List<EntyRecmaetarivalorDto> dtos) throws EBusinessException {
        try {
            List<EntyRecmaetarivalor> entities = new ArrayList<>();

            for (EntyRecmaetarivalorDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }
            dtos = new ArrayList<>();
            for (EntyRecmaetarivalor entity : repository.saveAll(entities)) {
                dtos.add(saveResponseTranslate.translate(entity));
            }
            return dtos;
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public EntyRecmaetarivalorDto update(Integer id, EntyRecmaetarivalorDto dto) throws EBusinessException {
        try {
            EntyRecmaetarivalor entity = dtoToEntityTranslate.translate(dto);
            EntyRecmaetarivalor old = repository.findById(id).get();

            old.setRecSecregRetp(
                    Objects.nonNull(dto.getRecSecregRetp()) && !entity.getRecSecregRetp().isEmpty()
                            ? entity.getRecSecregRetp()
                            : old.getRecSecregRetp());
            old.setApjNroregAphp(
                    Objects.nonNull(entity.getApjNroregAphp()) && !entity.getApjNroregAphp().equals(0)
                            ? entity.getApjNroregAphp()
                            : old.getApjNroregAphp());
            old.setRecTipresRepe(
                    Objects.nonNull(dto.getRecTipresRepe()) && !entity.getRecTipresRepe().isEmpty()
                            ? entity.getRecTipresRepe()
                            : old.getRecTipresRepe());
            old.setRecTituloRetp(
                    Objects.nonNull(dto.getRecTituloRetp()) && !entity.getRecTituloRetp().isEmpty()
                            ? entity.getRecTituloRetp()
                            : old.getRecTituloRetp());
            old.setRecNotmemRetp(
                    Objects.nonNull(dto.getRecNotmemRetp()) && !entity.getRecNotmemRetp().isEmpty()
                            ? entity.getRecNotmemRetp()
                            : old.getRecNotmemRetp());
            old.setRecNotdetRetp(
                    Objects.nonNull(dto.getRecNotdetRetp()) && !entity.getRecNotdetRetp().isEmpty()
                            ? entity.getRecNotdetRetp()
                            : old.getRecNotdetRetp());
            old.setRecImage1Retp(
                    Objects.nonNull(dto.getRecImage1Retp()) && !entity.getRecImage1Retp().isEmpty()
                            ? entity.getRecImage1Retp()
                            : old.getRecImage1Retp());
            old.setRecImage2Retp(
                    Objects.nonNull(dto.getRecImage2Retp()) && !entity.getRecImage2Retp().isEmpty()
                            ? entity.getRecImage2Retp()
                            : old.getRecImage2Retp());

            old.setRecImage3Retp(
                    Objects.nonNull(dto.getRecImage3Retp()) && !entity.getRecImage3Retp().isEmpty()
                            ? entity.getRecImage3Retp()
                            : old.getRecImage3Retp());

            old.setRecOrdvisRetp(
                    Objects.nonNull(entity.getRecOrdvisRetp()) && !entity.getRecOrdvisRetp().equals(0)
                            ? entity.getRecOrdvisRetp()
                            : old.getRecOrdvisRetp());

            old.setRecTipmonRetm(
                    Objects.nonNull(dto.getRecTipmonRetm()) && !entity.getRecTipmonRetm().isEmpty()
                            ? entity.getRecTipmonRetm()
                            : old.getRecTipmonRetm());

            old.setRecCanhorRetp(
                    Objects.nonNull(dto.getRecCanhorRetp()) && !entity.getRecCanhorRetp().equals(0)
                            ? entity.getRecCanhorRetp()
                            : old.getRecCanhorRetp() );

            old.setRecValhorRetp(
                    Objects.nonNull(dto.getRecValhorRetp()) && !entity.getRecValhorRetp().equals(0)
                            ? entity.getRecValhorRetp()
                            : old.getRecValhorRetp() );

            old.setRecPrecioRetp(
                    Objects.nonNull(dto.getRecPrecioRetp()) && !entity.getRecPrecioRetp().equals(0)
                            ? entity.getRecPrecioRetp()
                            : old.getRecPrecioRetp() );

            old.setRecEstregRetp(
                    Objects.nonNull(dto.getRecEstregRetp()) && !entity.getRecEstregRetp().isEmpty()
                            ? entity.getRecEstregRetp()
                            : old.getRecEstregRetp() );



            return saveResponseTranslate.translate(repository.save(old));
        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();

        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            Optional<EntyRecmaetarivalor> reg = repository.findById(id);
            if (reg.isEmpty() == false)
            {
                repository.delete(reg.get());
            }

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.DELETE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.DELETE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }


    private EntyRecmaetarivalorDto mapToDto(EntyRecmaetarivalor entyRecmaetarivalor){
        EntyRecmaetarivalorDto dto = new EntyRecmaetarivalorDto();
        dto.setRecUnikeyRetp(entyRecmaetarivalor.getRecUnikeyRetp());
        dto.setRecSecregRetp(entyRecmaetarivalor.getRecSecregRetp());
        dto.setApjNroregAphp(entyRecmaetarivalor.getApjNroregAphp());
        dto.setRecTipresRepe(entyRecmaetarivalor.getRecTipresRepe());
        dto.setRecTituloRetp(entyRecmaetarivalor.getRecTituloRetp());
        dto.setRecNotmemRetp(entyRecmaetarivalor.getRecNotmemRetp());
        dto.setRecNotdetRetp(entyRecmaetarivalor.getRecNotdetRetp());
        dto.setRecImage1Retp(entyRecmaetarivalor.getRecImage1Retp());
        dto.setRecImage2Retp(entyRecmaetarivalor.getRecImage2Retp());
        dto.setRecImage3Retp(entyRecmaetarivalor.getRecImage3Retp());
        dto.setRecOrdvisRetp(entyRecmaetarivalor.getRecOrdvisRetp());
        dto.setRecTipmonRetm(entyRecmaetarivalor.getRecTipmonRetm());
        dto.setRecCanhorRetp(entyRecmaetarivalor.getRecCanhorRetp());
        dto.setRecValhorRetp(entyRecmaetarivalor.getRecValhorRetp());
        dto.setRecPrecioRetp(entyRecmaetarivalor.getRecPrecioRetp());
        dto.setRecEstregRetp(entyRecmaetarivalor.getRecEstregRetp());
        dto.setRecTituloRepe("NA");
        return  dto;
    }

    public static PaginationResponse headResponse(int currentPage    ,int totalPageSize ,
                                                  long totalResults  ,int totalPages,
                                                  boolean hasNextPage,boolean hasPreviousPage,
                                                  String nextpageUrl , String previousPageUrl  )
    {
        return PaginationResponse.builder()
                .currentPage(currentPage)
                .totalPageSize(totalPageSize)
                .totalResults(totalResults)
                .totalPages(totalPages)
                .hasNextPage(hasNextPage)
                .hasPreviousPage(hasPreviousPage)
                .nextPageUrl(nextpageUrl)
                .previousPageUrl(previousPageUrl)
                .build();

    }

}
