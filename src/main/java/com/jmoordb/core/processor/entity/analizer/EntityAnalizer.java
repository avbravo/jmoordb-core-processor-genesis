package com.jmoordb.core.processor.entity.analizer;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.Embedded;
import com.jmoordb.core.annotation.Id;
import com.jmoordb.core.annotation.Ignore;
import com.jmoordb.core.annotation.Referenced;
import com.jmoordb.core.annotation.ViewReferenced;
import com.jmoordb.core.annotation.enumerations.AnnotationType;
import com.jmoordb.core.annotation.enumerations.GenerationType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.entity.model.EntityData;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 
 * @author avbravo
 * @@code Analiza la anotación @Entity y genera List<EntityField>
 */
public class EntityAnalizer {


    public EntityAnalizer() {
    }



// <editor-fold defaultstate="collapsed" desc="EntityAnalizer get(Element element, Messager messager, String database, TypeMirror typeEntity, List<EntityMethod> entityMethodList)">

    /**
     * Procesa los metodos definidos en la interface
     *
     * @param element
     * @return
     */
    public static EntityAnalizer get(Element element, Messager messager, String database, List<EntityField> entityFieldList, EntityData entityData) {
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

            EntityField entityField = new EntityField.Builder()
                    .nameOfMethod(nameOfField)
                    .returnTypeValue(type)                    
                    .annotationType(AnnotationType.NONE)
                    .returnType(ProcessorUtil.convertToReturnTypeOfField(type))
                    .typeReferenced(TypeReferenced.REFERENCED)
             
                    
                    .build();

           if(!ProcessorUtil.isValidAnnotationForEntity(v)){
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
                entityField.setAnnotationType(AnnotationType.ID);
                entityField.setId(id);
                if (!type.startsWith("java.lang.String") && !type.startsWith("java.lang.Long") && !type.startsWith("org.bson.types.ObjectId") && !type.startsWith("java.util.UUID")) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Must be of type String or Long or ObjectId or UUID", element);
                }
                if(id.strategy().equals(GenerationType.AUTO) &&  !type.startsWith("java.lang.Long")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable is only allowed for Long data types..", element);
                }
                if(id.strategy().equals(GenerationType.OBJECTID) &&  !type.startsWith("org.bson.types.ObjectId")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " OBJECTID is only allowed for org.bson.types.ObjectId data types..", element);
                }
                if(id.strategy().equals(GenerationType.NONE) &&  type.startsWith("org.bson.types.ObjectId")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " @Id for ObjectId is allowed only with OBJECTID..", element);
                }
                  if(id.strategy().equals(GenerationType.UUID) &&  !type.startsWith("java.util.UUID")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " UUID is only allowed for java.util.UUID data types..", element);
                }
                     if (id.strategy().equals(GenerationType.NONE) && (!type.startsWith("java.lang.Long") && !type.startsWith("java.lang.String") )) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable NONE is only allowed for Long or String data types..", element);
                }
                 if(entityField.getReturnTypeValue().equals("org.bson.types.ObjectId") && !entityField.getNameOfMethod().equals("_id")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Fields of type ObjectId as primary key must be named _id...", element); 
                 }
                
            }
            Column column = v.getAnnotation(Column.class);
            if (column != null) {
                entityField.setAnnotationType(AnnotationType.COLUMN);
                entityField.setColumn(column);
                if(entityField.getReturnTypeValue().equals("java.util.UUID")){
                     messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " UUID not available for @Column..", element);
                }
            }
            Embedded embedded = v.getAnnotation(Embedded.class);
            if (embedded != null) {
             
                entityField.setAnnotationType(AnnotationType.EMBEDDED);
                entityField.setEmbedded(embedded);

            }
            Referenced referenced = v.getAnnotation(Referenced.class);
            if (referenced != null) {

                entityField.setAnnotationType(AnnotationType.REFERENCED);
                entityField.setReferenced(referenced);
                entityField.setTypeReferenced(referenced.typeReferenced());
            }
            
            ViewReferenced viewReferenced = v.getAnnotation(ViewReferenced.class);
            if (viewReferenced != null) {

                entityField.setAnnotationType(AnnotationType.VIEWREFERENCED);
                entityField.setViewReferenced(viewReferenced);
                entityField.setTypeReferenced(viewReferenced.typeReferenced());
            }

            entityFieldList.add(entityField);
        }
        });

        return new EntityAnalizer();

    }
// </editor-fold>

    


}
