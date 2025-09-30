package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinaryDataStoreDTO implements Serializable {
   private Long id ;

    private String key;

    private byte[] data;

    private Boolean deleted ;

    private boolean enabled ;
}
