//Whenever something doesn't exist, instead of:
//repository.findById(id).orElseThrow();
// throw new ResourceNotFoundException("Company not found with id " + id);
package com.hireflow.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
