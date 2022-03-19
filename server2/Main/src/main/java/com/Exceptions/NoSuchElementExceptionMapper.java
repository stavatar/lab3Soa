package com.Exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.utils.Message;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;
@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(final NoSuchElementException jpe) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Not found elements");
        return Response.status(404).entity(message).build();
    }

}
