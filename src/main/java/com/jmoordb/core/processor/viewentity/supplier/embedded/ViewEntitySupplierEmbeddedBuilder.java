/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.embedded;

import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierEmbeddedBuilder {
    
    
     // <editor-fold defaultstate="collapsed" desc="String embeddedProcess(ViewEntityData viewEntityData, ViewEntityField viewEntityField)">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String embeddedProcess(ViewEntityData viewEntityData, ViewEntityField viewEntityField) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());
        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// Embedded List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Embedded Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
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
    
    
    
          // <editor-fold defaultstate="collapsed" desc="String toUpdate(ViewEntityData viewEntityData, ViewEntityField viewEntityField, String caracterComa)">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String toUpdate(ViewEntityData viewEntityData, ViewEntityField viewEntityField, String caracterComa) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());
        //Si es una lista
//        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + " .$[]\"," + fieldLower + "Supplier.toUpdate(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
//        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + ".$[]\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// Embedded List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Embedded Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Embedded Stream<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            /**
             * Embedded Simple
             */
           sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
           
            result += "\t// Embedded of " + fieldLower + "\n";


            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

       
    

   
}
