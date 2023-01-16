package com.jmoordb.core.processor.entity.supplier;

import com.jmoordb.core.annotation.Entity;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.methods.EntityField;
import java.util.*;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToDocument;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToReferenced;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToUpdate;
import com.jmoordb.core.processor.entity.supplier.generate.EntitySupplierGenerateToUpdateReferenced;

/**
 * This class only works if we add elements in proper sequence.
 */
public class EntitySupplierSource {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

    EntitySupplierSourceUtil entitySupplierSourceUtil = new EntitySupplierSourceUtil();

    public EntitySupplierSource() {

    }

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder init(Entity entity, EntityData entityData, List<EntityField> entityFieldList, String database, String collection,Element element)">
    public EntitySupplierSource init(Entity entity, EntityData entityData, List<EntityField> entityFieldList, String database, String collection, Element element) {
        builder.append(entitySupplierSourceUtil.definePackage(entityData.getPackageOfEntity()));
        builder.append(entitySupplierSourceUtil.generateImport(entity, entityData, element));
        builder.append(entitySupplierSourceUtil.addRequestScoped());
        builder.append(entitySupplierSourceUtil.defineClass(entityData.getEntityName() + "Supplier", " implements Serializable"));

        Boolean haveEmbedded = EntitySupplierSourceUtil.haveEmbedded(entityFieldList);
        Boolean haveReferenced = EntitySupplierSourceUtil.haveReferenced(entityFieldList);
        if (haveReferenced || haveEmbedded) {
            builder.append(entitySupplierSourceUtil.inject(entity, entityData, database, collection, entityFieldList, element, haveReferenced, haveEmbedded));
        }

        /**
         * Generar los metodos encontrados
         */
        if (entityFieldList.isEmpty()) {
            //   MessagesUtil.warning("No hay información de los métodos");
        } else {

            builder.append(EntitySupplier.get(entityData, entityFieldList, element));
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
            
            /**
             * Genera los metodos toUpdateReferenced que seran usados por los metodos Update cuendo es 
             * un Referenciado de tipo REFERENCED
             * toReferencedUpdate
             * 
             */
             //toReferenced
            builder.append(EntitySupplierGenerateToUpdateReferenced.toUpdateReferenced(entityData, entityFieldList, element));
            builder.append(EntitySupplierGenerateToUpdateReferenced.toUpdateListReferenced(entityData, entityFieldList, element));
            
            
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
    public EntitySupplierSource addEditorFoldStartx(String desc) {
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
    public EntitySupplierSource addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addField(String type, String identifier)">
    public EntitySupplierSource addField(String type, String identifier) {
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
    public EntitySupplierSource addConstructor(String accessModifier, List<String> fieldsToBind) {
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
    public EntitySupplierSource addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addMethod(MethodProcessorAux method)">

    public EntitySupplierSource addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder createSetterForField(String name)">
    public EntitySupplierSource createSetterForField(String name) {
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

    public EntitySupplierSource createGetterForField(String name) {
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
    public EntitySupplierSource addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addNestedClass(SupplierSourceBuilder jClass)">
    public EntitySupplierSource addNestedClass(EntitySupplierSource jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
