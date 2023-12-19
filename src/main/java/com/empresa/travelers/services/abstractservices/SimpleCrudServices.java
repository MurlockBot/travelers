package com.empresa.travelers.services.abstractservices;

public interface SimpleCrudServices<Request, Response, ID> {

    Response create(Request request);

    Response read(ID id);

    void delete(ID id);
}
