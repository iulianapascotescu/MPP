package ro.ubb.catalog.core.repository;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Getter
public abstract class CustomRepositorySupport {
    @PersistenceContext
    private EntityManager entityManager;
}
