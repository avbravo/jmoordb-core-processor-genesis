package com.jmoordb.core.processor.projection.supplier;

import com.jmoordb.core.annotation.Projection;
import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.processor.internal.MethodProcessorAux;
import com.jmoordb.core.processor.methods.ProjectionField;
import java.util.*;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.projection.supplier.generate.ProjectionSupplierGenerateToDocument;
import com.jmoordb.core.processor.projection.supplier.generate.ProjectionSupplierGenerateToReferenced;
import com.jmoordb.core.processor.projection.supplier.generate.ProjectionSupplierGenerateToUpdate;

/**
 * This class only works if we add elements in proper sequence.
 */
public class ProjectionSupplierSource {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";

    private StringBuilder builder = new StringBuilder();
    private String className;
    private Map<String, String> fields = new LinkedHashMap<>();

    ProjectionSupplierSourceUtil projectionSupplierSourceUtil = new ProjectionSupplierSourceUtil();

    public ProjectionSupplierSource() {

    }

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder init(Projection projection, ProjectionData projectionData, List<ProjectionField> projectionFieldList, String database, String collection,Element element)">
    public ProjectionSupplierSource init(Projection projection, ProjectionData projectionData, List<ProjectionField> projectionFieldList, String database, String collection, Element element) {
        builder.append(projectionSupplierSourceUtil.definePackage(projectionData.getPackageOfProjection()));
        builder.append(projectionSupplierSourceUtil.generateImport(projection, projectionData, element));
        builder.append(projectionSupplierSourceUtil.addRequestScoped());
        builder.append(projectionSupplierSourceUtil.defineClass(projectionData.getProjectionName() + "Supplier", " implements Serializable"));

        Boolean haveEmbedded = ProjectionSupplierSourceUtil.haveEmbedded(projectionFieldList);
        Boolean haveReferenced = ProjectionSupplierSourceUtil.haveReferenced(projectionFieldList);
        Boolean haveViewReferenced = ProjectionSupplierSourceUtil.haveViewReferenced(projectionFieldList);
        if (haveReferenced || haveEmbedded) {
            builder.append(projectionSupplierSourceUtil.inject(projection, projectionData, database, collection, projectionFieldList, element, haveReferenced, haveEmbedded));
        }

        /**
         * Generar los metodos encontrados
         */
        if (projectionFieldList.isEmpty()) {
            //   MessagesUtil.warning("No hay información de los métodos");
        } else {

            builder.append(ProjectionSupplier.get(projectionData, projectionFieldList, element));
//toDocument
            builder.append(ProjectionSupplierGenerateToDocument.toDocument(projectionData, projectionFieldList, element));
            builder.append(ProjectionSupplierGenerateToDocument.toDocumentList(projectionData, projectionFieldList, element));
            
            //toUpdate
            builder.append(ProjectionSupplierGenerateToUpdate.toUpdate(projectionData, projectionFieldList, element));
            builder.append(ProjectionSupplierGenerateToUpdate.toUpdateList(projectionData, projectionFieldList, element));
            
            /**
             * Metodos para devolver solo la llave primaria
             */
            //toReferenced
            builder.append(ProjectionSupplierGenerateToReferenced.toReferenced(projectionData, projectionFieldList, element));
            builder.append(ProjectionSupplierGenerateToReferenced.toReferencedList(projectionData, projectionFieldList, element));
            
          
            
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
    public ProjectionSupplierSource addEditorFoldStartx(String desc) {
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
    public ProjectionSupplierSource addFields(LinkedHashMap<String, String> identifierToTypeMap) {
        for (Map.Entry<String, String> entry : identifierToTypeMap.entrySet()) {
            addField(entry.getValue(), entry.getKey());
        }
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addField(String type, String identifier)">
    public ProjectionSupplierSource addField(String type, String identifier) {
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
    public ProjectionSupplierSource addConstructor(String accessModifier, List<String> fieldsToBind) {
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
    public ProjectionSupplierSource addConstructor(String accessModifier, boolean bindFields) {
        addConstructor(accessModifier,
                bindFields ? new ArrayList(fields.keySet())
                        : new ArrayList<>());
        return this;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addMethod(MethodProcessorAux method)">

    public ProjectionSupplierSource addMethod(MethodProcessorAux method) {
        builder.append(LINE_BREAK)
                .append(method.end())
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder createSetterForField(String name)">
    public ProjectionSupplierSource createSetterForField(String name) {
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

    public ProjectionSupplierSource createGetterForField(String name) {
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
    public ProjectionSupplierSource addEditorFoldStart(String desc) {
        builder.append("// <editor-fold defaultstate=\"collapsed\" desc=\"")
                .append(desc)
                .append("\">")
                .append(LINE_BREAK);
        return this;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SupplierSourceBuilder addNestedClass(SupplierSourceBuilder jClass)">
    public ProjectionSupplierSource addNestedClass(ProjectionSupplierSource jClass) {
        builder.append(LINE_BREAK);
        builder.append(jClass.end());
        builder.append(LINE_BREAK);
        return this;
    }
// </editor-fold>
}
