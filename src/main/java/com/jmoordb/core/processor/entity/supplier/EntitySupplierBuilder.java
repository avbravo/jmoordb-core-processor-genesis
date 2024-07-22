package com.jmoordb.core.processor.entity.supplier;

import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateGet;
import com.jmoordb.core.annotation.Entity;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateGetId;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGeneratePutObjectId;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.fields.EntityField;
import java.util.*;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToDocument;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToReferenced;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToUpdate;

/**
 * This class only works if we add elements in proper sequence.
 */
public class EntitySupplierBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

    EntitySupplierBuilderUtil entitySupplierSourceUtil = new EntitySupplierBuilderUtil();

    public EntitySupplierBuilder() {

    }

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder init(Entity entity, EntityData entityData, List<EntityField> entityFieldList, String database, String collection,Element element)">
    public EntitySupplierBuilder init(Entity entity, EntityData entityData, List<EntityField> entityFieldList, String database, String collection, Element element) {
        builder.append(entitySupplierSourceUtil.definePackage(entityData.getPackageOfEntity()));
        builder.append(entitySupplierSourceUtil.generateImport(entity, entityData, element));
        builder.append(entitySupplierSourceUtil.addRequestScoped());
        builder.append(entitySupplierSourceUtil.defineClass(entityData.getEntityName() + "Supplier", " implements Serializable"));

        Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
        Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);
        Boolean haveViewReferenced = EntitySupplierBuilderUtil.haveViewReferenced(entityFieldList);
        if (haveReferenced || haveEmbedded || haveViewReferenced) {
            builder.append(entitySupplierSourceUtil.inject(entity, entityData, database, collection, entityFieldList, element, haveReferenced, haveEmbedded,haveViewReferenced));
        }

        /**
         * Generar los metodos encontrados
         */
        if (entityFieldList.isEmpty()) {
            //   MessagesUtil.warning("No hay información de los métodos");
        } else {

            //get
            builder.append(EntitySupplierGenerateGet.get(entityData, entityFieldList, element));
            //getId
            builder.append(EntitySupplierGenerateGetId.getId(entityData, entityFieldList, element));
            //putObjectId
            builder.append(EntitySupplierGeneratePutObjectId.putObjectId(entityData, entityFieldList, element));
            
            
//toDocument
            builder.append(EntitySupplierGenerateToDocument.toDocument(entityData, entityFieldList, element));
            builder.append(EntitySupplierGenerateToDocument.toDocumentList(entityData, entityFieldList, element));
            
            //toUpdate
            builder.append(EntitySupplierGenerateToUpdate.toUpdate(entityData, entityFieldList, element));
            builder.append(EntitySupplierGenerateToUpdate.toUpdateList(entityData, entityFieldList, element));
            
            /**
             * Metodos para devolver solo la llave primaria
             */
            //toReferenced
            builder.append(EntitySupplierGenerateToReferenced.toReferenced(entityData, entityFieldList, element));
            builder.append(EntitySupplierGenerateToReferenced.toReferencedList(entityData, entityFieldList, element));
            
          
            
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
    public EntitySupplierBuilder addEditorFoldStartx(String desc) {
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
    public EntitySupplierBuilder addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addField(String type, String identifier)">
    public EntitySupplierBuilder addField(String type, String identifier) {
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
    public EntitySupplierBuilder addConstructor(String accessModifier, List<String> fieldsToBind) {
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
    public EntitySupplierBuilder addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addMethod(MethodProcessorAux method)">

    public EntitySupplierBuilder addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder createSetterForField(String name)">
    public EntitySupplierBuilder createSetterForField(String name) {
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

    public EntitySupplierBuilder createGetterForField(String name) {
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
    public EntitySupplierBuilder addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addNestedClass(SupplierSourceBuilder jClass)">
    public EntitySupplierBuilder addNestedClass(EntitySupplierBuilder jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
