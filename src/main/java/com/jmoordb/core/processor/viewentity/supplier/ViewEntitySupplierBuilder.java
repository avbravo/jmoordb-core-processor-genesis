package com.jmoordb.core.processor.viewentity.supplier;

import com.jmoordb.core.annotation.ViewEntity;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGeneratePutObjectId;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.methods.ViewEntityField;
import java.util.*;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateGet;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateGetId;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGeneratePutObjectId;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateToDocument;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateToReferenced;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateToUpdate;

/**
 * This class only works if we add elements in proper sequence.
 */
public class ViewEntitySupplierBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

   ViewEntitySupplierBuilderUtil viewEntitySupplierSourceUtil = new ViewEntitySupplierBuilderUtil();

    public ViewEntitySupplierBuilder() {

    }

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder init(ViewEntity viewEntity, ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, String database, String collection,Element element)">
    public ViewEntitySupplierBuilder init(ViewEntity viewEntity, ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, String database, String collection, Element element) {
        builder.append(viewEntitySupplierSourceUtil.definePackage(viewEntityData.getPackageOfViewEntity()));
        builder.append(viewEntitySupplierSourceUtil.generateImport(viewEntity, viewEntityData, element));
        builder.append(viewEntitySupplierSourceUtil.addRequestScoped());
        builder.append(viewEntitySupplierSourceUtil.defineClass(viewEntityData.getEntityName() + "Supplier", " implements Serializable"));

        Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
        Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);
        Boolean haveViewReferenced = ViewEntitySupplierBuilderUtil.haveViewReferenced(viewEntityFieldList);
        if (haveReferenced || haveEmbedded || haveViewReferenced) {
            builder.append(viewEntitySupplierSourceUtil.inject(viewEntity, viewEntityData, database, collection, viewEntityFieldList, element, haveReferenced, haveEmbedded,haveViewReferenced));
        }

        /**
         * Generar los metodos encontrados
         */
        if (viewEntityFieldList.isEmpty()) {
            //   MessagesUtil.warning("No hay información de los métodos");
        } else {
//get
            builder.append(ViewEntitySupplierGenerateGet.get(viewEntityData, viewEntityFieldList, element));
//getId
 //putObjectId
            builder.append(ViewEntitySupplierGeneratePutObjectId.putObjectId(viewEntityData, viewEntityFieldList, element));
            
            builder.append(ViewEntitySupplierGenerateGetId.getId(viewEntityData, viewEntityFieldList, element));
//toDocument
            builder.append(ViewEntitySupplierGenerateToDocument.toDocument(viewEntityData, viewEntityFieldList, element));
            builder.append(ViewEntitySupplierGenerateToDocument.toDocumentList(viewEntityData, viewEntityFieldList, element));
            
            //toUpdate
            builder.append(ViewEntitySupplierGenerateToUpdate.toUpdate(viewEntityData, viewEntityFieldList, element));
            builder.append(ViewEntitySupplierGenerateToUpdate.toUpdateList(viewEntityData, viewEntityFieldList, element));
            
            /**
             * Metodos para devolver solo la llave primaria
             */
            //toReferenced
            builder.append(ViewEntitySupplierGenerateToReferenced.toReferenced(viewEntityData, viewEntityFieldList, element));
            builder.append(ViewEntitySupplierGenerateToReferenced.toReferencedList(viewEntityData, viewEntityFieldList, element));
            
          
            
        }

        return this;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public ViewEntitySupplierBuilder addEditorFoldStartx(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap)">
    /**
     *
     * @param identifierToTypeMap
     * @return
     */
    public ViewEntitySupplierBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addField(String type, String identifier)">
    public ViewEntitySupplierBuilder addField(String type, String identifier) {
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

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addConstructor(String accessModifier, List<String> fieldsToBind) ">
    /**
     *
     * @param accessModifier
     * @param fieldsToBind
     * @return
     */
    public ViewEntitySupplierBuilder addConstructor(String accessModifier, List<String> fieldsToBind) {
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

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addConstructor(String accessModifier, boolean bindFields)">
    public ViewEntitySupplierBuilder addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addMethod(MethodProcessorAux method)">

    public ViewEntitySupplierBuilder addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder createSetterForField(String name)">
    public ViewEntitySupplierBuilder createSetterForField(String name) {
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
// <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder createGetterForField(String name)">

    public ViewEntitySupplierBuilder createGetterForField(String name) {
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
    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addEditorFoldStart(String desc) ">
    /**
     *
     * @param desc. -Utiloce \" si necesita incluir " en el texto
     * @return inserta un editor fold que sirve como ayuda a NetBeans IDE
     */
    public ViewEntitySupplierBuilder addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addNestedClass(SupplierSourceBuilder jClass)">
    public ViewEntitySupplierBuilder addNestedClass(ViewEntitySupplierBuilder jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
