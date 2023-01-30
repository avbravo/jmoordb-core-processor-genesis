/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.builder.interfaces;

import static com.jmoordb.core.annotation.enumerations.TypeReferenced.EMBEDDED;
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.methods.EntityField;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface SupplierReferencedGetBuilder {
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
//        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";
//                result += "\t" + fieldUpper + " " + fieldLower + " = (" + fieldUpper + ") document_.get(\"" + fieldLower + "\");\n";
                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(EntityData entityData, EntityField entityField)">

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
//        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";
//                result += "\t" + fieldUpper + " " + fieldLower + " = (" + fieldUpper + ") document_.get(\"" + fieldLower + "\");\n";
                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(entityData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
  
          

    // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String referencedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField, Element element) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfDocumentEmbeddableFile(element, fieldUpper + ".java", idData);

        String as = entityField.getReferenced().from();

        String foreignField = idData.getFieldName();

        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = entityField.getReferenced().from();
        String localField = entityField.getReferenced().localField();

        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";

        var isEmbeddedReferenced = Boolean.FALSE;
        switch (entityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }

                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }
                return result;
            }

            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";


                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result += "\t// Referenced of " + fieldLower + "\n";
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String viewReferencedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField, Element element) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfDocumentEmbeddableFile(element, fieldUpper + ".java", idData);

        String as = entityField.getReferenced().from();

        String foreignField = idData.getFieldName();

        String foreignFieldUpper = JmoordbCoreUtil.letterToUpper(idData.getFieldName());
        String from = entityField.getReferenced().from();
        String localField = entityField.getReferenced().localField();

        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";

        var isEmbeddedReferenced = Boolean.FALSE;
        switch (entityField.getTypeReferenced()) {
            case EMBEDDED:
                isEmbeddedReferenced = Boolean.TRUE;
            default:
                isEmbeddedReferenced = Boolean.FALSE;

        }
        try {

            if (entityField.getReturnTypeValue().contains("List")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced List<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }

                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {

                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + entityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
                }
                return result;
            }

            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";


                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result += "\t// Referenced of " + fieldLower + "\n";
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, entityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
    
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

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";
//                result += "\t" + fieldUpper + " " + fieldLower + " = (" + fieldUpper + ") document_.get(\"" + fieldLower + "\");\n";
                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String referencedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField)">

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

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";
                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Set<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                if (!isEmbeddedReferenced) {
                    result += "\t// Referenced Stream<" + fieldLower + ">\n";

                    result += "\t List<Document> " + fieldLower + "DocumentList = (List)document_.get(\"" + viewEntityField.getViewReferenced().from() + "\");\n";
                    result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";

                    result += "\tfor( Document " + fieldLower + "Doc :" + fieldLower + "DocumentList){\n";
                    result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new," + fieldLower + "Doc);\n";

                    result += "\t\t Optional<" + fieldUpper + "> " + fieldLower + "Optional = " + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + JmoordbCoreUtil.letterToUpper(viewEntityField.getViewReferenced().localField()) + "());\n";

                    result += "\t\tif(" + fieldLower + "Optional.isPresent()){" + "\n";
                    result += "\t\t\t" + fieldLower + "List.add(" + fieldLower + "Optional.get());\n";
                    result += "\t\t}\n";
                    result += "\t}\n";
                    result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                    result += sourceSupplier;
                } else {
                    result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
                }
                return result;
            }
            if (!isEmbeddedReferenced) {
                result += "\t// @Referenced of [" + fieldLower + " how Referenced]\n";
//                result += "\t" + fieldUpper + " " + fieldLower + " = (" + fieldUpper + ") document_.get(\"" + fieldLower + "\");\n";
                result += "\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get("
                        + fieldUpper + "::new,(Document) document_.get(\"" + fieldLower + "\"));\n";

                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "Repository.findByPk(" + fieldLower + ".get" + foreignFieldUpper + "()).get());\n";

            } else {
                result = SupplierEmbeddedGetBuilder.embeddedProcessGet(viewEntityData, viewEntityField);
            }
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
}
