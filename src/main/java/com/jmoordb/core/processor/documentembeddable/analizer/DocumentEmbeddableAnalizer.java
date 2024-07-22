package com.jmoordb.core.processor.documentembeddable.analizer;

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
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 *
 * @author avbravo
 * @@code Analiza la anotación @DocumentEmbeddable y genera
 * List<DocumentEmbeddableField>
 */
public class DocumentEmbeddableAnalizer {
// <editor-fold defaultstate="collapsed" desc="fields()">

    // </editor-fold>
    public DocumentEmbeddableAnalizer() {
    }

// <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableAnalizer get(Element element, Messager messager, String database, TypeMirror typeDocumentEmbeddable, List<DocumentEmbeddableMethod> documentEmbeddableMethodList)">
    /**
     * Procesa los metodos definidos en la interface
     *
     * @param element
     * @return
     */
    public static DocumentEmbeddableAnalizer get(Element element, Messager messager, String database, List<DocumentEmbeddableField> documentEmbeddableFieldList, DocumentEmbeddableData documentEmbeddableData) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        List<String> mandatoryFields = new ArrayList<>();

        Stream<VariableElement> stream = ElementFilter.fieldsIn(element.getEnclosedElements()).stream();
        stream.forEach(v -> {
            Ignore ignore = v.getAnnotation(Ignore.class);
            if (ignore != null) {
                /**
                 * Campos ignore no se toman en cuenta
                 */
            } else {

                String nameOfField = v.getSimpleName().toString();

                String type = ProcessorUtil.getTypeOfField(v);

                DocumentEmbeddableField documentEmbeddableField = new DocumentEmbeddableField.Builder()
                        .nameOfMethod(nameOfField)
                        .returnTypeValue(type)
                        .annotationType(AnnotationType.NONE)
                        .returnType(ProcessorUtil.convertToReturnTypeOfField(type))
                        .typeReferenced(TypeReferenced.REFERENCED)
                        .build();

                if (!ProcessorUtil.isValidAnnotationForDocumentEmbeddable(v)) {
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
                    documentEmbeddableField.setAnnotationType(AnnotationType.ID);
                    documentEmbeddableField.setId(id);
                    if (!type.startsWith("java.lang.String") && !type.startsWith("java.lang.Long") && !type.startsWith("org.bson.types.ObjectId") && !type.startsWith("java.util.UUID")) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Must be of type String or Long or ObjectId or UUID", element);
                    }
                    if (id.strategy().equals(GenerationType.AUTO) && !type.startsWith("java.lang.Long")) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable is only allowed for Long data types..", element);
                    }
                    if (id.strategy().equals(GenerationType.NONE) && (!type.startsWith("java.lang.Long") && !type.startsWith("java.lang.String"))) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Autoincrementable NONE is only allowed for Long or String data types..", element);
                    }
                    if (id.strategy().equals(GenerationType.OBJECTID)) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Embedded documents do not allow @ID with OBJECTID..", element);
                    }
                    if (id.strategy().equals(GenerationType.UUID)) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " Embedded documents do not allow @ID with UUID..", element);
                    }

                }
                Column column = v.getAnnotation(Column.class);
                if (column != null) {
                    documentEmbeddableField.setAnnotationType(AnnotationType.COLUMN);
                    documentEmbeddableField.setColumn(column);
                    if (documentEmbeddableField.getReturnTypeValue().equals("java.util.UUID")) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " UUID not available for @Column..", element);
                    }
                    if (documentEmbeddableField.getReturnTypeValue().equals("org.bson.types.ObjectId")) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Field: " + nameOfField + " ObjectId not available for @Column..", element);
                    }
                    
                    
                    
                }
                Embedded embedded = v.getAnnotation(Embedded.class);
                if (embedded != null) {

                    documentEmbeddableField.setAnnotationType(AnnotationType.EMBEDDED);
                    documentEmbeddableField.setEmbedded(embedded);

                }
                Referenced referenced = v.getAnnotation(Referenced.class);
                if (referenced != null) {

                    documentEmbeddableField.setAnnotationType(AnnotationType.REFERENCED);
                    documentEmbeddableField.setReferenced(referenced);
                    documentEmbeddableField.setTypeReferenced(referenced.typeReferenced());
                }
                ViewReferenced viewReferenced = v.getAnnotation(ViewReferenced.class);
                if (viewReferenced != null) {

                    documentEmbeddableField.setAnnotationType(AnnotationType.VIEWREFERENCED);
                    documentEmbeddableField.setViewReferenced(viewReferenced);
                    documentEmbeddableField.setTypeReferenced(viewReferenced.typeReferenced());
                }

                documentEmbeddableFieldList.add(documentEmbeddableField);
            }
        });

        return new DocumentEmbeddableAnalizer();

    }
// </editor-fold>

}
