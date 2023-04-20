package com.makiia.crosscutting.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntyRecmaetarivalorResponse {
    private  String rspValue ="OK";
    private  String rspMessage ="OK";
    private  String rspParentKey = "NA";
    private  String rspAppKey = "NA";
    private  PaginationResponse rspPagination = new PaginationResponse();
    private  List<EntyRecmaetarivalorDto> rspData;

}

