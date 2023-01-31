/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.entity.supplier.referenced;

import static com.jmoordb.core.annotation.enumerations.TypeReferenced.EMBEDDED;
import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.processor.entity.supplier.embedded.EntitySupplierEmbeddedGetBuilder;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface EntitySupplierReferencedGetBuilder {
    // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(EntityData entityData, EntityField entityField)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String referencedProcessGet(EntityData entityData, EntityField entityField, Element element) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = entityField.getReferenced().from();

        String foreignField = idData.getFieldName();
        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = entityField.getReferenced().from();
        String formUpperCase = JmoordbCoreUtil.letterToUpper(from);
        String localField = entityField.getReferenced().localField();
        var isEmbeddedReferenced = Boolean.FALSE;
        switch (entityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }

        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";
//                result += "\t" + fieldUpper + " " + fieldLower + " = (" + fieldUpper + ") document_.get(\"" + fieldLower + "\");\n";
                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String viewReferencedProcessGet(EntityData entityData, EntityField entityField)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String viewReferencedProcessGet(EntityData entityData, EntityField entityField, Element element) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = entityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = entityField.getViewReferenced().from();
        String formUpperCase = JmoordbCoreUtil.letterToUpper(from);
        String localField = entityField.getViewReferenced().localField();
        
        var isEmbeddedReferenced = Boolean.FALSE;
        switch (entityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }

        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";


                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId("
                        + fieldUpper + "::new,(Document) document_.get(\"" + entityField.getViewReferenced().from() + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = EntitySupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
  
          

    
    

            
}
