/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.generate.util;

import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierViewReferencedUtil {
    // <editor-fold defaultstate="collapsed" desc="String referencedProcess(ViewEntityData viewEntityData, ViewEntityField viewEntityField,Boolean typeReferencedEmbedded)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewEntityField
     * @return
     */
    public static String viewReferencedProcess(ViewEntityData viewEntityData,
            ViewEntityField viewEntityField, Element element, Boolean typeReferencedEmbedded) {
        String result = "";
        String viewEntityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String viewEntityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfViewEntityFile(element, fieldUpper + ".java", idData);

        String as = viewEntityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = viewEntityField.getViewReferenced().from();
        String localField = viewEntityField.getViewReferenced().localField();
        String sourceSupplier = "";
        if (typeReferencedEmbedded) {
            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ");\n";
        } else {
            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ");\n";
        }

        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
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



    // <editor-fold defaultstate="collapsed" desc="String referencedProcessUpdate(ViewEntityData viewEntityData, ViewEntityField viewEntityField String caracterComa,String caracterComa,Boolean typeReferencedEmbedded)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param viewEntityData
     * @param viewEntityField
     * @return
     */
    public static String toUpdate(ViewEntityData viewEntityData, ViewEntityField viewEntityField, Element element, String caracterComa, Boolean typeReferencedEmbedded) {
        String result = "";
        String viewEntityNameUpper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
        String viewEntityNameLower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(viewEntityField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfViewEntityFile(element, fieldUpper + ".java", idData);

        String as = viewEntityField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = viewEntityField.getViewReferenced().from();
        String localField = viewEntityField.getViewReferenced().localField();
        /**
         * Para listas
         */

        String fromViewReferenced = viewEntityField.getViewReferenced().from();
 String sourceSupplier = "";
        if (typeReferencedEmbedded) {
            
             sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced+ "\"," + fieldLower + "Supplier.toDocument(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        }else{
            

             sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toReferenced(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        }
        
        try {

            if (viewEntityField.getReturnTypeValue().contains("List")) {

                result += "\t// ViewReferenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Set")) {
                result += "\t// ViewReferenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (viewEntityField.getReturnTypeValue().contains("Stream")) {
                result += "\t// ViewReferenced Stream<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }

            /**
             * Referencias simples
             */
              if (typeReferencedEmbedded) {
            sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced+ "\"," + fieldLower + "Supplier.toUpdate(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
              }else{

                sourceSupplier = "\t\tUpdates.set(\"" + fromViewReferenced + "\"," + fieldLower + "Supplier.toReferenced(" + viewEntityNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            }
            result += "\t// ViewReferenced of " + fieldLower + "\n";

            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>


}
