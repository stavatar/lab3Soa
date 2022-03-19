package com.Exceptions;
import javax.ws.rs.*;
import com.utils.Message;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
@Override
public Response toResponse(final ProcessingException jpe) {
        Message message=new Message();
        message.setCode(0);
         message.setData("Server number 1 is unvaliable");
          return Response.status(504).entity(message).build();

}

 }