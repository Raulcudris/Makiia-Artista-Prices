package com.makiia.crosscutting.persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recmaetarivalor")

public class EntyRecmaetarivalor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rec_unikey_retp")
    private Integer recUnikeyRetp;

    @Basic(optional = false)
    @Column(name = "rec_secreg_retp")
    private String recSecregRetp;

    @Basic(optional = false)
    @Column(name = "apj_nroreg_aphp")
    private String apjNroregAphp;

    @Basic(optional = false)
    @Column(name = "rec_tipres_repe")
    private String recTipresRepe;

    @Basic(optional = false)
    @Column(name = "rec_titulo_retp")
    private String recTituloRetp;

    @Basic(optional = false)
    @Column(name = "rec_notmem_retp")
    private String recNotmemRetp;

    @Basic(optional = false)
    @Column(name = "rec_notdet_retp")
    private String recNotdetRetp;

    @Basic(optional = false)
    @Column(name = "rec_image1_retp")
    private String recImage1Retp;

    @Basic(optional = false)
    @Column(name = "rec_image2_retp")
    private String recImage2Retp;

    @Basic(optional = false)
    @Column(name = "rec_image3_retp")
    private String recImage3Retp;

    @Basic(optional = false)
    @Column(name = "rec_ordvis_retp")
    private Integer recOrdvisRetp;

    @Basic(optional = false)
    @Column(name = "rec_tipmon_retm")
    private String recTipmonRetm;

    @Basic(optional = false)
    @Column(name = "rec_canhor_retp")
    private Integer recCanhorRetp;

    @Basic(optional = false)
    @Column(name = "rec_valhor_retp")
    private Double recValhorRetp;

    @Basic(optional = false)
    @Column(name = "rec_precio_retp")
    private Double recPrecioRetp;

    @Basic(optional = false)
    @Column(name = "rec_estreg_retp")
    private String recEstregRetp;

}
