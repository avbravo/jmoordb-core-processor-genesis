/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.builder.interfaces;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.builder.SupplierSourceBuilderUtil;
import static com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedBuilder.embeddedProcess;
import static com.jmoordb.core.processor.builder.interfaces.SupplierReferencedBuilder.referencedProcess;
import com.jmoordb.core.processor.methods.EntityField;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface SupplierToJsonBuilder {
    
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toJson(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toJson(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = SupplierSourceBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = SupplierSourceBuilderUtil.haveReferenced(entityFieldList);

            String sentence = "\t StringBuilder sb = new StringBuilder();\n";
            sentence += "\t\tsb.append(\"{\");\n";
            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n,";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\\n, \"";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += embeddedProcess(entityData, entityField);
                        } else {
                            sentence += "+" + coma + referencedProcess(entityData, entityField, element);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }

                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tsb.append(\"" + coma + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\\\":\\\"\").append(" + getMethod + ").append(\"\\\"\");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        if (entityField.getReturnType().equals(ReturnType.DATE)) {
                            sentence += "\t\tjava.util.Date " + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "IsoDate = com.jmoordb.core.util.JmoordbCoreDateUtil.stringToDate(com.jmoordb.core.util.JmoordbCoreDateUtil.iSODate(" + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()));\n";

                            String isoDate = "com.jmoordb.core.util.JmoordbCoreDateUtil.iSODate(" + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "())";
                            sentence += "\t\tsb.append(\"" + coma + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\\\":\\\"\").append(" + isoDate + ").append(\"\\\"\");\n";

                        } else {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                            sentence += "\t\tsb.append(\"" + coma + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\\\":\\\"\").append(" + getMethod + ").append(\"\\\"\");\n";
                        }

                        count++;
                        break;

                }

            }

            sentence += "\t\tsb.append(\n\"}\");\n";
            sentence += "\treturn sb.toString();\n";
            String code
                    = ProcessorUtil.editorFoldToJson(entityData) + "\n\n"
                    + "    public String toJson(" + entityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ") {\n"
                    + sentence + "\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
   
}
