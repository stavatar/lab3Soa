package com.Exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.utils.Message;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
    @Override
    public Response toResponse(final JsonParseException jpe) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Invalid data supplied for request");
        return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                .entity(message).build();
    }
}
