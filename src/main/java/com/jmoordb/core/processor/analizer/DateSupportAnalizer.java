package com.jmoordb.core.processor.analizer;

import com.jmoordb.core.annotation.enumerations.AnnotationType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import com.jmoordb.core.processor.fields.DateSupportField;
import com.jmoordb.core.processor.model.DateSupportData;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;

/**
 * 
 * @author avbravo
 * @@code Analiza la anotación @DateSupport y genera List<DateSupportField>
 */
public class DateSupportAnalizer {
// <editor-fold defaultstate="collapsed" desc="DateSupportAnalizer()">

  
  
    // </editor-fold>

    public DateSupportAnalizer() {
    }



// <editor-fold defaultstate="collapsed" desc="DateSupportAnalizer get(Element element, Messager messager, String database, TypeMirror typeDateSupport, List<DateSupportMethod> dateSupportMethodList)">

    /**
     * Procesa los metodos definidos en la interface
     *
     * @param element
     * @return
     */
    public static DateSupportAnalizer get(Element element, Messager messager,List<DateSupportField> dateSupportFieldList, DateSupportData dateSupportData) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        List<String> mandatoryFields = new ArrayList<>();

        Stream<VariableElement> stream = ElementFilter.fieldsIn(element.getEnclosedElements()).stream();
        stream.forEach(v -> {
            String nameOfField = v.getSimpleName().toString();

            String type = ProcessorUtil.getTypeOfField(v);

            DateSupportField dateSupportField = new DateSupportField.Builder()
                    .nameOfMethod(nameOfField)
                    .returnTypeValue(type)                    
                    .annotationType(AnnotationType.NONE)
                    .returnType(ProcessorUtil.convertToReturnTypeOfField(type))
                    .typeReferenced(TypeReferenced.REFERENCED)
                    
                    .build();

            
           /**
            * 
            */
            final TypeElement classElem = (TypeElement) element;
            final String prefix = System.getProperty("user.dir");
            final String className = classElem.getQualifiedName().toString();
            String fileName = prefix + "/src/main/java/" + className.replace('.', '/') + ".java";

    


          
            dateSupportFieldList.add(dateSupportField);
        });

        return new DateSupportAnalizer();

    }
// </editor-fold>

    


}
