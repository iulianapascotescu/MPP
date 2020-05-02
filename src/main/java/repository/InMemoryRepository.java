package repository;

import domain.BaseEntity;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class InMemoryRepository<ID extends Serializable, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Map<ID, T> entities;
    private Validator<T> validator;

    /**
     * Constructor of the in memory repository
     * Returns nothing.
     *
     * @param validator Validator instance
     */
    public InMemoryRepository(Validator<T> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    /**
     * Returns an optional of the entity that is searched by it's unique id.
     * If id is null an exception may be thrown.
     *
     * @param id ID (generic type)
     * @return object defined by the id
     * @throws IllegalArgumentException if id is <code>null</code> or doesn't exist
     */
    @Override
    public Optional<T> findOne(ID id) {
        Optional.ofNullable(entities.get(id)).orElseThrow(() -> new IllegalArgumentException("id must not be null and must exist"));
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Returns an iterable of all the entities in our repository.
     *
     * @return the set of entities
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *           if the entity is not valid.
     *
     */
     @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional.ofNullable(entity).orElseThrow(() -> new IllegalArgumentException("Entity must not be null"));
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes an entity from the repository.
     * If id is null an exception may be thrown.
     *
     * @param id ID type (generic type)
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if id is <code>null</code>
     */
    @Override
    public Optional<T> delete(ID id) {
        Optional.ofNullable(entities.get(id)).orElseThrow(() -> new IllegalArgumentException("id must not be null and must exist"));
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Optional.ofNullable(entity).orElseThrow(() -> new IllegalArgumentException("id must not be null and must exist"));
        validator.validate(entity);

        AtomicReference<Optional<T>> p = new AtomicReference<>(Optional.empty());
        Optional.ofNullable(entities.get(entity.getId())).ifPresentOrElse((v)->entities.replace(entity.getId(),entity),()->{
            p.set(Optional.of(entity));});
        return p.get();
    }
}
