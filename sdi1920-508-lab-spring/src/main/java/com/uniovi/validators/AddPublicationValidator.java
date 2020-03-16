package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import com.uniovi.entities.Publication;

@Component
public class AddPublicationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Publication.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty.title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "Error.empty.content");
	}

}
