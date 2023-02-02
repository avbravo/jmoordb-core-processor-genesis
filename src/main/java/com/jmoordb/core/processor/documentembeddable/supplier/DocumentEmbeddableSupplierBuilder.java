package com.jmoordb.core.processor.documentembeddable.supplier;

import com.jmoordb.core.processor.documentembeddable.supplier.generate.DocumentEmbeddableSupplierGenerateGet;
import com.jmoordb.core.annotation.DocumentEmbeddable;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.DocumentEmbeddableSupplierGenerateGetId;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import java.util.*;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.DocumentEmbeddableSupplierGenerateToDocument;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.DocumentEmbeddableSupplierGenerateToReferenced;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.DocumentEmbeddableSupplierGenerateToUpdate;

/**
 * This class only works if we add elements in proper sequence.
 */
public class DocumentEmbeddableSupplierBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

    DocumentEmbeddableSupplierBuilderUtil sourceDocumentEmbeddableUtilBuilder = new DocumentEmbeddableSupplierBuilderUtil();

    public DocumentEmbeddableSupplierBuilder() {

    }

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder init(DocumentEmbeddable documentEmbeddable, DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, String database, String collection,Element element)">
    public DocumentEmbeddableSupplierBuilder init(DocumentEmbeddable documentEmbeddable, DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, String database, String collection, Element element) {
        builder.append(sourceDocumentEmbeddableUtilBuilder.definePackage(documentEmbeddableData.getPackageOfDocumentEmbeddable()));
        builder.append(sourceDocumentEmbeddableUtilBuilder.generateImport(documentEmbeddable, documentEmbeddableData, element));
        builder.append(sourceDocumentEmbeddableUtilBuilder.addRequestScoped());
        builder.append(sourceDocumentEmbeddableUtilBuilder.defineClass(documentEmbeddableData.getDocumentEmbeddableName() + "Supplier", " implements Serializable"));

        Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
        Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);
        Boolean haveViewReferenced = DocumentEmbeddableSupplierBuilderUtil.haveViewReferenced(documentEmbeddableFieldList);
        if (haveReferenced || haveEmbedded || haveViewReferenced) {
            builder.append(sourceDocumentEmbeddableUtilBuilder.inject(documentEmbeddable, documentEmbeddableData, database, collection, documentEmbeddableFieldList, element, haveReferenced, haveEmbedded,haveViewReferenced));
        }

        /**
         * Generar los metodos encontrados
         */
        if (documentEmbeddableFieldList.isEmpty()) {
            //   MessagesUtil.warning("No hay información de los métodos");
        } else {
//get
            builder.append(DocumentEmbeddableSupplierGenerateGet.get(documentEmbeddableData, documentEmbeddableFieldList, element));
//getId
            builder.append(DocumentEmbeddableSupplierGenerateGetId.getId(documentEmbeddableData, documentEmbeddableFieldList, element));

            
            //ToDocument
            builder.append(DocumentEmbeddableSupplierGenerateToDocument.toDocument(documentEmbeddableData, documentEmbeddableFieldList, element));
            builder.append(DocumentEmbeddableSupplierGenerateToDocument.toDocumentList(documentEmbeddableData, documentEmbeddableFieldList, element));

            //toUpdate
            builder.append(DocumentEmbeddableSupplierGenerateToUpdate.toUpdate(documentEmbeddableData, documentEmbeddableFieldList, element));
            builder.append(DocumentEmbeddableSupplierGenerateToUpdate.toUpdateList(documentEmbeddableData, documentEmbeddableFieldList, element));
            
            //toReferenced
             builder.append(DocumentEmbeddableSupplierGenerateToReferenced.toReferenced(documentEmbeddableData, documentEmbeddableFieldList, element));
            builder.append(DocumentEmbeddableSupplierGenerateToReferenced.toReferencedList(documentEmbeddableData, documentEmbeddableFieldList, element));

            
            
        }

        //    builder.append(FindByPkOfDocumentEmbeddableBuilder.findByPKOfDocumentEmbeddable(repositoryData));
        return this;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public DocumentEmbeddableSupplierBuilder addEditorFoldStartx(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addFields(LinkedHashMap<String, String> identifierToTypeMap)">
    /**
     *
     * @param identifierToTypeMap
     * @return
     */
    public DocumentEmbeddableSupplierBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addField(String type, String identifier)">
    public DocumentEmbeddableSupplierBuilder addField(String type, String identifier) {
        fields.put(identifier, type);
        builder.append("private ")
                .append(type)
                .append(" ")
                .append(identifier)
                .append(";")
                .append(LINE_BREAK);

        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addConstructor(String accessModifier, List<String> fieldsToBind) ">
    /**
     *
     * @param accessModifier
     * @param fieldsToBind
     * @return
     */
    public DocumentEmbeddableSupplierBuilder addConstructor(String accessModifier, List<String> fieldsToBind) {
        builder.append(LINE_BREAK)
                .append(accessModifier)
                .append(" ")
                .append(className)
                .append("(");

        for (int i = 0; i < fieldsToBind.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            String name = fieldsToBind.get(i);
            builder.append(fields.get(name))
                    .append(" ")
                    .append(name);
        }
        builder.append(") {");
        for (int i = 0; i < fieldsToBind.size(); i++) {
            builder.append(LINE_BREAK);

            String name = fieldsToBind.get(i);
            builder.append("this.")
                    .append(name)
                    .append(" = ")
                    .append(name)
                    .append(";");
        }
        builder.append(LINE_BREAK);
        builder.append("}");
        builder.append(LINE_BREAK);

        return this;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addConstructor(String accessModifier, boolean bindFields)">
    public DocumentEmbeddableSupplierBuilder addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addMethod(MethodProcessorAux method)">

    public DocumentEmbeddableSupplierBuilder addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource createSetterForField(String name)">
    public DocumentEmbeddableSupplierBuilder createSetterForField(String name) {
        if (!fields.containsKey(name)) {
            throw new IllegalArgumentException("Field not found for setter: " + name);
        }
        addMethod(new MethodProcessorAux()
                .defineSignature("public", false, "void")
                .name("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
                .defineBody(" this." + name + " = " + name + ";"));
        return this;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource createGetterForField(String name)">

    public DocumentEmbeddableSupplierBuilder createGetterForField(String name) {
        if (!fields.containsKey(name)) {
            throw new IllegalArgumentException("Field not found for Getter: " + name);
        }
        addMethod(new MethodProcessorAux()
                .defineSignature("public", false, fields.get(name))
                .name("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1))
                .defineBody(" return this." + name + ";"));
        return this;
    }
    // </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="end()">

    /**
     *
     * @return
     */
    public String end() {
        builder.append(LINE_BREAK + "}");
        return builder.toString();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public DocumentEmbeddableSupplierBuilder addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DocumentEmbeddableSupplierSource addNestedClass(SupplierSourceBuilder jClass)">
    public DocumentEmbeddableSupplierBuilder addNestedClass(DocumentEmbeddableSupplierBuilder jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
