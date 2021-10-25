package com.xib.assessment.exception;

import com.xib.assessment.apirerror.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ApiError.class, NotFoundError.class, BadRequestError.class, ExistsError.class,
            AuthenticationError.class, WebServiceError.class,
            ServiceSubExistError.class, MandatoryFieldError.class})
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof NotFoundError) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            NotFoundError notFoundError = (NotFoundError) ex;

            return handleNotFoundException(notFoundError, headers, status, request);
        } else if (ex instanceof MandatoryFieldError) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            MandatoryFieldError mandatoryFieldError = (MandatoryFieldError) ex;

            return handleMandatoryFieldException(mandatoryFieldError, headers, status, request);
        } else if (ex instanceof ServiceSubExistError) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ServiceSubExistError serviceSubExistError = (ServiceSubExistError) ex;

            return handleServiceSubExistException(serviceSubExistError, headers, status, request);
        } else if (ex instanceof AuthenticationError) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            AuthenticationError authenticationError = (AuthenticationError) ex;

            return handleAuthenticationError(authenticationError, headers, status, request);
        } else if (ex instanceof WebServiceError) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            WebServiceError webServiceError = (WebServiceError) ex;

            return handleWebServiceException(webServiceError, headers, status, request);
        }  else if (ex instanceof BadRequestError) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            BadRequestError badRequestError = (BadRequestError) ex;

            return handleWebServiceException(badRequestError, headers, status, request);
        }  else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(new ApiError(ex.getMessage()), headers, status, request);
        }
    }

    protected ResponseEntity<Object> handleNotFoundException(NotFoundError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleMandatoryFieldException(MandatoryFieldError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleServiceSubExistException(ServiceSubExistError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleAuthenticationError(AuthenticationError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleWebServiceException(WebServiceError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleWebServiceException(BadRequestError ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        if (ex instanceof MandatoryFieldError) {
            body.put("errorType", ((MandatoryFieldError) ex).getErrorType());
            body.put("errorMessage", ((MandatoryFieldError) ex).getErrorMessage());
        } else if (ex instanceof ServiceSubExistError) {
            body.put("errorType", ((ServiceSubExistError) ex).getErrorType());
            body.put("errorMessage", ((ServiceSubExistError) ex).getErrorMessage());
        } else if (ex instanceof AuthenticationError) {
            body.put("errorType", ((AuthenticationError) ex).getErrorType());
            body.put("errorMessage", ((AuthenticationError) ex).getErrorMessage());
        } else if (ex instanceof WebServiceError) {
            body.put("errorType", ((WebServiceError) ex).getErrorType());
            body.put("errorMessage", ((WebServiceError) ex).getErrorMessage());
        } else if (ex instanceof NotFoundError) {
            body.put("errorType", ((NotFoundError) ex).getErrorType());
            body.put("errorMessage", ((NotFoundError) ex).getErrorMessage());
        } else if (ex instanceof ApiError) {
            body.put("errorType", ((ApiError) ex).getErrorType());
            body.put("errorMessage", ((ApiError) ex).getErrorMessage());
        } else {
            return new ResponseEntity<>(ex.getMessage(), headers, status);
        }

        body.put("statusCode", status.value());
        body.put("status", status);

        return new ResponseEntity<>(body, headers, status);
    }
}
