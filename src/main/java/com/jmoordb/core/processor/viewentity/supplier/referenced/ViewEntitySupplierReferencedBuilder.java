/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.referenced;

import com.jmoordb.core.processor.methods.ViewEntityField;
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
public interface ViewEntitySupplierReferencedBuilder {
     
    
    
      // <editor-fold defaultstate="collapsed" desc="String referencedProcess(ViewEntityData viewEntityData, ViewEntityField viewViewEntityField)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewViewEntityField
     * @return
     */
    public static String referencedProcess(ViewEntityData viewEntityData, ViewEntityField viewViewEntityField, Element element) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewViewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = viewViewEntityField.getReferenced().from();

        String foreignField = idData.getFieldName();
        String from = viewViewEntityField.getReferenced().from();
        String localField = viewViewEntityField.getReferenced().localField();
        String sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ");\n";
        try {

            if (viewViewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";


                result += sourceSupplier;
                return result;
            }
            if (viewViewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";


                result += sourceSupplier;
                return result;
            }
            if (viewViewEntityField.getReturnTypeValue().contains("Stream")) {
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
    
        // <editor-fold defaultstate="collapsed" desc="String toUpdate(ViewEntityData viewEntityData, ViewEntityField viewViewEntityField, Element element, String caracterComa)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewViewEntityField
     * @return
     */
    public static String toUpdate(ViewEntityData viewEntityData, ViewEntityField viewViewEntityField, Element element, String caracterComa) {
        String result = "";
        String entityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String entityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewViewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfEntityFile(element, fieldUpper + ".java", idData);

        String as = viewViewEntityField.getReferenced().from();

        String foreignField = idData.getFieldName();
        String from = viewViewEntityField.getReferenced().from();
        String localField = viewViewEntityField.getReferenced().localField();
       /**
        * Para listas 
        */  
//        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + ".$[]\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
        try {

            if (viewViewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";


                result += sourceSupplier;
                return result;
            }
            if (viewViewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";


                result += sourceSupplier;
                return result;
            }
            if (viewViewEntityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Referenced Stream<" + fieldLower + ">\n";


                result += sourceSupplier;
                return result;
            }
            
            /**
             * Referencias simples
             */
            sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + entityNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
            result += "\t// Referenced of " + fieldLower + "\n";


            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    
}
