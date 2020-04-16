package interfaces;

import java.util.List;

public interface CrudService<T> {
    List<T> getAll();

    T getById(int id);

    T create(T object);

    T update(T object);

    void delete(T object);
}
