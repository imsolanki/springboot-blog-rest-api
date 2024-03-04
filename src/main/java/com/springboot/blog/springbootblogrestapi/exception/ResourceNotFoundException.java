//3
package com.springboot.blog.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long fieldValue;


    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }
}
/*
* Post Entity-->Repository-->exception(if resource not found)
* Whenever Create Custom Exception always extend it with Runtime Exception
* Use @ResponseCode to change the response HTTP code
* post not found with fieldName with fieldValue
* from here go and create DTO's(client to dto to controller & vice versa(best practice))
 */