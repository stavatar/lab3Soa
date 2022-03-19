package com.Exceptions;

import com.utils.Message;
import org.hibernate.exception.JDBCConnectionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
public class OtherExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(final Exception jpe) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Unknown Error! " +jpe.getClass()+" "+ jpe.getCause()+" "+ jpe.getMessage() +" " + jpe.getLocalizedMessage() +" " + Arrays.toString(jpe.getStackTrace()));
        return Response.status(503).entity(message).build();
    }
}