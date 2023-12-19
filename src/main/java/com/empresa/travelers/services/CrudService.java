package com.empresa.travelers.services;

public interface CrudService<Request, Response, ID> {

    Response create(Request request);
    Response read(ID id);
    Response update(Request request, ID id);
    void delete(ID id);
}
