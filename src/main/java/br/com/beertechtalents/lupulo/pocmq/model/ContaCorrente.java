package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID hash;

    @PrePersist
    private void prePersist() {
        this.hash = UUID.randomUUID();
    }

}
