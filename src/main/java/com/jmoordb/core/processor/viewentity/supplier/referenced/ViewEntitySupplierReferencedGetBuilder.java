/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.referenced;

import static com.jmoordb.core.annotation.enumerations.TypeReferenced.EMBEDDED;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.viewentity.supplier.embedded.ViewEntitySupplierEmbeddedGetBuilder;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierReferencedGetBuilder {
    
          
          // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewEntityField
     * @return
     */
    public static String referencedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField, Element element) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = viewEntityField.getReferenced().from();

        String foreignField = idData.getFieldName();
        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = viewEntityField.getReferenced().from();
        String formUpperCase = JmoordbCoreUtil.letterToUpper(from);
        String localField = viewEntityField.getReferenced().localField();
        var isEmbeddedReferenced = Boolean.FALSE;
        switch (viewEntityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }
//        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        String sourceSupplier = "\t\t\n";
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";
                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";

                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";

                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
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
                result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String viewReferencedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewEntityField
     * @return
     */
    public static String viewReferencedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField, Element element) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = viewEntityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = viewEntityField.getViewReferenced().from();
        String formUpperCase = JmoordbCoreUtil.letterToUpper(from);
        String localField = viewEntityField.getViewReferenced().localField();
        
        var isEmbeddedReferenced = Boolean.FALSE;
        switch (viewEntityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }
//        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        String sourceSupplier = "\t\t\n";
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";
                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
 result += "\tif( " + fieldLower + "List == null || " + fieldLower + "List.isEmpty()){\n";
                    result += "\n";
                    result += "\n\t}else{\n";
                    result += "\t\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t\t}\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";

                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.getId("
                        + fieldUpper + "::new,(Document) document_.get(\"" + viewEntityField.getViewReferenced().from() + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = ViewEntitySupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
    
    

            
}
