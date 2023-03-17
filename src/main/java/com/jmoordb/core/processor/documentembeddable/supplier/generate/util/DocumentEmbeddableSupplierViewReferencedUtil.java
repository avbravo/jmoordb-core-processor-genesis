/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.documentembeddable.supplier.generate.util;

import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.util.ConsoleUtil;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface DocumentEmbeddableSupplierViewReferencedUtil {

    // <editor-fold defaultstate="collapsed" desc="String referencedProcess(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField,, Boolean typeReferencedEmbedded)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String referencedProcess(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField,
        Element element, Boolean typeReferencedEmbedded) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfDocumentEmbeddableFile(element, fieldUpper + ".java", idData);

        String as = entityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = entityField.getViewReferenced().from();
        String localField = entityField.getViewReferenced().localField();
          String fromViewReferenced = entityField.getViewReferenced().from();
        String sourceSupplier = "";
        
      
        if (typeReferencedEmbedded) {
//            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
            sourceSupplier = "\t\tdocument_.put(\"" + fromViewReferenced+ "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        } else {
//            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
            sourceSupplier = "\t\tdocument_.put(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";

        }
        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Referenced Stream<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }

            result += "\t// Referenced of " + fieldLower + "\n";

            result += sourceSupplier;
            
            

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String toUpdate(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField,String caracterComa,Boolean typeReferencedEmbedded)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String toUpdate(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField, Element element, String caracterComa, Boolean typeReferencedEmbedded) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfDocumentEmbeddableFile(element, fieldUpper + ".java", idData);

        String as = entityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = entityField.getViewReferenced().from();
        String localField = entityField.getViewReferenced().localField();
            String fromViewReferenced = entityField.getViewReferenced().from();
        /**
         * Listas de referencias
         */
//        String sourceSupplier= "\t\tUpdates.set(\"" + fieldLower+ ".$[]\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";

        String sourceSupplier = "";
        if (typeReferencedEmbedded) {
//            sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toUpdate(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toUpdate(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        } else {

//            sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        }

        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Referenced Stream<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }else{
                 /**
             * Referencias simples
             */
            if (typeReferencedEmbedded) {
//                sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toUpdate(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
                sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toUpdate(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            } else {
//                sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
                sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toReferenced(" + entityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            }

            result += "\t// Referenced of " + fieldLower + "\n";

            result += sourceSupplier;
            }

           

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
}
