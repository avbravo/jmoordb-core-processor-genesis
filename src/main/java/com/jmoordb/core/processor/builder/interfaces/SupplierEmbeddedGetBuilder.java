/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.builder.interfaces;

import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.methods.EntityField;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;


/**
 *
 * @author avbravo
 */
public interface SupplierEmbeddedGetBuilder {
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
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t};\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\n\t// Embedded Set<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";
                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";

                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\n\t// Embedded Stream<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                result += sourceSupplier;
                return result;
            }
            result += "\n\t// Embedded of [" + fieldLower + "]\n";
         //   result += "\t" + entityNameLower + ".set" + fieldUpper + "((" + fieldUpper + ") document_.get(\"" + fieldLower + "\"));\n";
            result += "\t" + entityNameLower + ".set" + fieldUpper + "("+fieldLower+"Supplier.get("+
                     fieldUpper +"::new,(Document) document_.get(\"" + fieldLower + "\")));\n";

            
//            animal.setEspecie(especieSupplier.get(Especie::new, (Document) document_.get("especie")));
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
   
    
    // <editor-fold defaultstate="collapsed" desc="String embeddedProcessGet(embeddedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField))">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String embeddedProcessGet(ViewEntityData viewEntityData, ViewEntityField viewEntityField) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());

        String sourceSupplier = "\t\t\n";
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {

                result += "\n\t// Embedded List<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t};\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\n\t// Embedded Set<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";
                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";

                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                result += "\n\t// Embedded Stream<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                result += sourceSupplier;
                return result;
            }
            result += "\n\t// Embedded of [" + fieldLower + "]\n";
         //   result += "\t" + entityNameLower + ".set" + fieldUpper + "((" + fieldUpper + ") document_.get(\"" + fieldLower + "\"));\n";
            result += "\t" + entityNameLower + ".set" + fieldUpper + "("+fieldLower+"Supplier.get("+
                     fieldUpper +"::new,(Document) document_.get(\"" + fieldLower + "\")));\n";

            
//            animal.setEspecie(especieSupplier.get(Especie::new, (Document) document_.get("especie")));
            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
   
    
    
    

    // <editor-fold defaultstate="collapsed" desc="String embeddedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField)">
    /**
     * Procesa los documentos embebidos
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String embeddedProcessGet(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());
        String sourceSupplier = "\t\t\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\n\t// Embedded List<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List);\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\n\t// Embedded Set<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";
                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";

                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(new java.util.HashSet<>(" + fieldLower + "List));\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\n\t// Embedded Stream<" + fieldLower + ">\n";
                result += "\tList<" + fieldUpper + "> " + fieldLower + "List = new ArrayList<>();\n";
                result += "\tList<Document> " + fieldLower + "Doc = (List) document_.get(\"" + fieldLower + "\");\n";
                result += "\tfor( Document doc" + fieldUpper + " : " + fieldLower + "Doc){\n";

                result += "\t\t" + fieldUpper + " " + fieldLower + " = " + fieldLower + "Supplier.get(" + fieldUpper + "::new, doc" + fieldUpper + ");\n";
                result += "\t\t" + fieldLower + "List.add(" + fieldLower + ");\n";
                result += "\t}\n";
                result += "\t" + entityNameLower + ".set" + fieldUpper + "(" + fieldLower + "List.stream());\n";

                result += sourceSupplier;
                return result;
            }
            result += "\n\t// Embedded of [" + fieldLower + "]\n";
//            result += "\t" + entityNameLower + ".set" + fieldUpper + "((" + fieldUpper + ") document_.get(\"" + fieldLower + "\"));\n";
result += "\t" + entityNameLower + ".set" + fieldUpper + "("+fieldLower+"Supplier.get("+
                     fieldUpper +"::new,(Document) document_.get(\"" + fieldLower + "\")));\n";


            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

   
}
