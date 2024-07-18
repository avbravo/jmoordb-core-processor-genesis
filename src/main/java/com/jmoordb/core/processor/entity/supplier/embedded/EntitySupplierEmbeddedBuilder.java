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
public interface EntitySupplierEmbeddedBuilder {
     // <editor-fold defaultstate="collapsed" desc="String embeddedProcess(EntityData entityData, EntityField entityField)">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String embeddedProcess(EntityData entityData, EntityField entityField) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());
        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
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
    
    
    
    
          // <editor-fold defaultstate="collapsed" desc="String toUpdate(EntityData entityData, EntityField entityField, String caracterComa)">

    /**
     * Procesa los documentos embebidos
     *
     * @param entityData
     * @param entityField
     * @return
     */
    public static String toUpdate(EntityData entityData, EntityField entityField, String caracterComa) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod());
        //Si es una lista
        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        
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
