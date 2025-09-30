package com.cdl.escrow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateDocumentDTO implements Serializable {
    private Long id ;

    private String rea;

    private String documentName;

    private String content;

    private String location;

    private String module;

    private Long recordId;

    private String storageType;

    private ZonedDateTime uploadDate;

    private String documentSize;

    private ZonedDateTime validityDate;

    private int version;

    private String eventDetail;

    private ApplicationSettingDTO documentTypeDTO;

    private boolean enabled;

    private Boolean deleted ;
}
