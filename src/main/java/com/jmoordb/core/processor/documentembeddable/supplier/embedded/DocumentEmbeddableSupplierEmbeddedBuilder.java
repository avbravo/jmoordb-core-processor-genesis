/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.documentembeddable.supplier.embedded;

import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;

/**
 *
 * @author avbravo
 */
public interface DocumentEmbeddableSupplierEmbeddedBuilder {
    
    

    
    
    
    
     // <editor-fold defaultstate="collapsed" desc="String embeddedProcess(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField)">

    /**
     * Procesa los documentos embebidos
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String embeddedProcess(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());
        String sourceSupplier= "\t\tdocument_.put(\"" + fieldLower+ "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\t// Embedded List<" + fieldLower + ">\n";

                 result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Embedded Set<" + fieldLower + ">\n";

                  result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Embedded Stream<" + fieldLower + ">\n";

               result += sourceSupplier;
                return result;
            }
            result += "\t// Embedded of " + fieldLower + "\n";
       
 
  result += sourceSupplier;
  
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
    
    
    
    

    

       
    
 // <editor-fold defaultstate="collapsed" desc="String toUpdate(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField documentEmbeddableField, String caracterComa)">

    /**
     * Procesa los documentos embebidos
     *
     * @param documentEmbeddableData
     * @param documentEmbeddableField
     * @return
     */
    public static String toUpdate(DocumentEmbeddableData documentEmbeddableData, DocumentEmbeddableField entityField, String caracterComa) {
        String result = "";
        String documentEmbeddableNameUpper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());
        
        /**
         * Coleccion de Referencias
         */
//        String sourceSupplier= "\t\tUpdates.set(\"" + fieldLower+ ".$[]\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        String sourceSupplier= "\t\tUpdates.set(\"" + fieldLower+ "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        try {

            if (entityField.getReturnTypeValue().contains("List")) {

                result += "\t// Embedded List<" + fieldLower + ">\n";

                 result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Embedded Set<" + fieldLower + ">\n";
         
                  result += sourceSupplier;
                return result;
            }
            if (entityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Embedded Stream<" + fieldLower + ">\n";

               result += sourceSupplier;
                return result;
            }
            /**
             * Simple
             */
           sourceSupplier= "\t\tUpdates.set(\"" + fieldLower+ "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
            result += "\t// Embedded of " + fieldLower + "\n";
       
 
  result += sourceSupplier;
  
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
   
}
