package br.edu.ifpb.infra.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDate;

@FacesValidator("dateValidator")
public class DataValidador implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LocalDate data = (LocalDate) value;
        if(data==null){
            throw  new ValidatorException(new FacesMessage("Data incorreta"));
        }
        if(data.isEqual(LocalDate.now())||data.isAfter(LocalDate.now())){
            throw  new ValidatorException(new FacesMessage("Data futura"));
        }
    }
}
