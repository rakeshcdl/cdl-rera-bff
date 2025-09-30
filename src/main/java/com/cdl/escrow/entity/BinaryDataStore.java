package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * A store that manages binary data in a key-value fashion.
 * Typically used for storing non-textual data such as images, files, documents, or other binary blobs.
 * The 'key' is used to uniquely identify the binary data, and the 'value' represents the actual binary content.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "binary_data_store")
public class BinaryDataStore implements Serializable {
    @Id
    @SequenceGenerator(
            name = "binary_data_store_id_seq_gen",
            sequenceName = "binary_data_store_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "binary_data_store_id_seq_gen"
    )
    private Long id ;

    private String key;

    private byte[] data;
    private Boolean enabled ;

    private Boolean deleted;


}
