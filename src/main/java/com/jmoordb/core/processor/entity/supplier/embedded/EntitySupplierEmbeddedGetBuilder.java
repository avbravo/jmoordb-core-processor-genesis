/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.entity.supplier.embedded;

import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;

/**
 *
 * @author avbravo
 */
public interface EntitySupplierEmbeddedGetBuilder {
    // <editor-fold defaultstate="collapsed" desc="String embeddedProcessGet(EntityData entityData, EntityField entityField)">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String embeddedProcessGet(EntityData entityData, EntityField entityField) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\n\t// Embedded List<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";

                result += "\tif( " + fieldLower + "Doc == null || " + fieldLower + "Doc.isEmpty()){\n";
                result += "\n";
                result += "\n\t}else{\n";
                result += "\t\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t\t}\n";
                result += "\t}\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\n\t// Embedded Set<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tif( " + fieldLower + "Doc == null || " + fieldLower + "Doc.isEmpty()){\n";
                result += "\n";
                result += "\n\t}else{\n";
                result += "\t\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";
                result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";

                result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t\t}\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\n\t// Embedded Stream<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";

                result += "\tif( " + fieldLower + "Doc == null || " + fieldLower + "Doc.isEmpty()){\n";
                result += "\n";
                result += "\n\t}else{\n";
                result += "\t\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t\t}\n";
                 result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                result += sourceSupplier;
                return result;
            }
            result += "\n\t// Embedded of [" + fieldLower + "]\n";
            
            result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Supplier.get("
                    + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\")));\n";


            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

}
