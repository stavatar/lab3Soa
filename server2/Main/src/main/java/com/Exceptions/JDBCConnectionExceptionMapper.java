package com.Exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.utils.Message;
import org.hibernate.exception.JDBCConnectionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



@Provider
public class JDBCConnectionExceptionMapper implements ExceptionMapper<JDBCConnectionException> {
    @Override
    public Response toResponse(final JDBCConnectionException jpe) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Error! No database connection ");
        return Response.status(503).entity(message).build();
    }
}