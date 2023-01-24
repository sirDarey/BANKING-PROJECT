package sirdarey.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
	
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleCustomExceptions (Exception ex) {
		Map <String,String> errorMap = new HashMap<>();
		errorMap.put ("Error Message", ex.getMessage())	;
		return errorMap;
	}

}
