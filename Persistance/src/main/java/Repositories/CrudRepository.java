package Repositories;

import Entities.Entity;

public interface CrudRepository<ID, entity extends Entity<ID>> {

    void add(entity e);
    default void delete(ID id) {
    }
    void update(entity e);
    default entity findOne(ID id) {
        return null;
    }
    Integer size();

}
