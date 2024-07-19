package com.jmoordb.core.processor.viewentity.analizer;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.Embedded;
import com.jmoordb.core.annotation.Id;
import com.jmoordb.core.annotation.Ignore;
import com.jmoordb.core.annotation.Referenced;
import com.jmoordb.core.annotation.ViewReferenced;
import com.jmoordb.core.annotation.enumerations.AnnotationType;
import com.jmoordb.core.annotation.enumerations.GenerationType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 
 * @author avbravo
 * @@code Analiza la anotación @ViewEntity y genera List<ViewEntityField>
 */
public class ViewEntityAnalizer {


    public ViewEntityAnalizer() {
    }



// <editor-fold defaultstate="collapsed" desc="ViewEntityAnalizer get(Element element, Messager messager, String database, TypeMirror typeViewEntity, List<ViewEntityMethod> viewEntityMethodList)">

    /**
     * Procesa los metodos definidos en la interface
     *
     * @param element
     * @return
     */
    public static ViewEntityAnalizer get(Element element, Messager messager, String database, List<ViewEntityField> viewEntityFieldList, ViewEntityData viewEntityData) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        List<String> mandatoryFields = new ArrayList<>();

        Stream<VariableElement> stream = ElementFilter.fieldsIn(element.getEnclosedElements()).stream();
        stream.forEach(v -> {
                     Ignore ignore = v.getAnnotation(Ignore.class);
            if (ignore != null) {
/**
 * Campos ignore no se toman en cuenta
 */
                
            }else{
            String nameOfField = v.getSimpleName().toString();

            String type = ProcessorUtil.getTypeOfField(v);

            ViewEntityField viewEntityField = new ViewEntityField.Builder()
                    .nameOfMethod(nameOfField)
                    .returnTypeValue(type)                    
                    .annotationType(AnnotationType.NONE)
                    .returnType(ProcessorUtil.convertToReturnTypeOfField(type))
                    .typeReferenced(TypeReferenced.REFERENCED)
                    
                    .build();

           if(!ProcessorUtil.isValidAnnotationForViewEntity(v)){
                messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " You do not have a valid annotation.", element);
                return;
           }
            
           /**
            * 
            */
            final TypeElement classElem = (TypeElement) element;
            final String prefix = System.getProperty("user.dir");
            final String className = classElem.getQualifiedName().toString();
            String fileName = prefix + "/src/main/java/" + className.replace('.', '/') + ".java";

    


            Id id = v.getAnnotation(Id.class);
            if (id != null) {
                viewEntityField.setAnnotationType(AnnotationType.ID);
                viewEntityField.setId(id);
                if (!type.startsWith("java.lang.String") && !type.startsWith("java.lang.Long") && !type.startsWith("org.bson.types.ObjectId") && !type.startsWith("java.util.UUID")) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Must be of type String or Long or ObjectId or UUID", element);
                }
                if(id.strategy().equals(GenerationType.AUTO) &&  !type.startsWith("java.lang.Long")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable is only allowed for Long data types..", element);
                }
                 if(id.strategy().equals(GenerationType.OBJECTID) &&  !type.startsWith("org.bson.types.ObjectId")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " OBJECTID is only allowed for org.bson.types.ObjectId data types..", element);
                }
                 if(id.strategy().equals(GenerationType.UUID) &&  !type.startsWith("java.util.UUID")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " UUID is only allowed for java.util.UUID data types..", element);
                }
                  if(id.strategy().equals(GenerationType.NONE) &&  type.startsWith("org.bson.types.ObjectId")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " @Id for ObjectId is allowed only with OBJECTID..", element);
                }
                      if (id.strategy().equals(GenerationType.NONE) && (!type.startsWith("java.lang.Long") && !type.startsWith("java.lang.String") )) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable NONE is only allowed for Long or String data types..", element);
                }
            }
            Column column = v.getAnnotation(Column.class);
            if (column != null) {
                viewEntityField.setAnnotationType(AnnotationType.COLUMN);
                viewEntityField.setColumn(column);
                if(viewEntityField.getReturnTypeValue().equals("java.util.UUID")){
                     messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " UUID not available for @Column..", element);
                }
            }
            Embedded embedded = v.getAnnotation(Embedded.class);
            if (embedded != null) {
             
                viewEntityField.setAnnotationType(AnnotationType.EMBEDDED);
                viewEntityField.setEmbedded(embedded);

            }
            Referenced referenced = v.getAnnotation(Referenced.class);
            if (referenced != null) {

                viewEntityField.setAnnotationType(AnnotationType.REFERENCED);
                viewEntityField.setReferenced(referenced);
                viewEntityField.setTypeReferenced(referenced.typeReferenced());
            }
            
            ViewReferenced viewReferenced = v.getAnnotation(ViewReferenced.class);
            if (viewReferenced != null) {

                viewEntityField.setAnnotationType(AnnotationType.VIEWREFERENCED);
                viewEntityField.setViewReferenced(viewReferenced);
                viewEntityField.setTypeReferenced(viewReferenced.typeReferenced());
            }

            viewEntityFieldList.add(viewEntityField);
        }
        });

        return new ViewEntityAnalizer();

    }
// </editor-fold>

    


}
