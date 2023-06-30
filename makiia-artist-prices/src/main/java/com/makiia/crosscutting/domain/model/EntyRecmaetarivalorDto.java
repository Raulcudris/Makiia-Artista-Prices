package com.makiia.crosscutting.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntyRecmaetarivalorDto {
    private Integer recUnikeyRetp;
    private String  recSecregRetp;
    private String  apjNroregAphp;
    private String  recTipresRepe;
    private String  recTituloRetp;
    private String  recNotmemRetp;
    private String  recNotdetRetp;
    private String  recImage1Retp;
    private String  recImage2Retp;
    private String  recImage3Retp;
    private Integer recOrdvisRetp;
    private String  recTipmonRetm;
    private Integer recCanhorRetp;
    private Double  recValhorRetp;
    private Double  recPrecioRetp;
    private String  recEstregRetp;

}
