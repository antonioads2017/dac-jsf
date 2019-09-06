package br.edu.ifpb.infra.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter("dateConverter")
public class DataConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String date) {
        if(date.isEmpty()){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
            return localDate;
        }catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value==null){
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = ((LocalDate) value).format(dateTimeFormatter);
        return date;
    }
}
