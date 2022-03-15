package lab2.stavatar.serveronelab2.Exception;


import lab2.stavatar.serveronelab2.NotFoundObjException;
import lab2.stavatar.serveronelab2.Utils.Message;
import org.hibernate.exception.JDBCConnectionException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.hibernate.exception.DataException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.NoSuchElementException;


@ControllerAdvice
public class Advice {
    @ExceptionHandler({NotFoundObjException.class, NoSuchElementException.class})
    public ResponseEntity<Message> handleExceptionNotFound(Exception e) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Not found  element: " + e.getMessage() + " stackTrace=" + e.getStackTrace());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,ServletRequestBindingException.class,MethodArgumentNotValidException.class,MissingServletRequestParameterException.class, HttpMessageNotWritableException.class, HttpMessageConversionException.class,HttpMessageNotReadableException.class,IllegalArgumentException.class, JSONException.class,DataException.class})
    public ResponseEntity<Message> handleExceptionArg(Exception e) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Error! Invalid input data: " + e.getMessage() + " stackTrace=" + e.getStackTrace());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<Message> handleExceptionJDBC(Exception e) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Error! No database connection : " + e.getMessage() + " stackTrace=" + e.getStackTrace());
        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException(Exception e) {
        Message message=new Message();
        message.setCode(0);
        message.setData("Request failed: " + e.getClass() +" "+e.getMessage() + " stackTrace=" + e.getStackTrace());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
