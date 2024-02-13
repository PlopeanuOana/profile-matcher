package com.gameloft.profile.advisor;

import com.gameloft.profile.advisor.exception.MatchException;
import com.gameloft.profile.advisor.model.ErrorDTO;
import com.gameloft.profile.advisor.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleTypeMismatch(final HttpMediaTypeNotAcceptableException exception) {
        log.error(exception.getMessage(), exception);

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return new ErrorDTO(badRequest.value(), badRequest.name(), exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(final NotFoundException exception) {
        log.error(exception.getMessage(), exception);

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return new ErrorDTO(notFound.value(), notFound.name(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ExceptionHandler(MatchException.class)
    public void handleMatchException(final MatchException matchException) {
        log.info(matchException.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDTO handleRunTimeException(RuntimeException runtimeException) {
        log.error(runtimeException.getMessage(), runtimeException);

        HttpStatus internal = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ErrorDTO(internal.value(), internal.name(), "Please check logs for more details");
    }
}
