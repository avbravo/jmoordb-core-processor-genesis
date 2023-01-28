/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.projection.supplier.generate.util;

import com.jmoordb.core.processor.methods.ProjectionField;
import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.processor.model.IdData;
import com.jmoordb.core.util.JmoordbCoreFileUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface ProjectionSupplierViewReferencedUtil {
    // <editor-fold defaultstate="collapsed" desc="String referencedProcess(ProjectionData projectionData, ProjectionField projectionField,Boolean typeReferencedEmbedded)">

    /**
     * Procesa los documentos Referenciados
     *
     * @param projectionData
     * @param projectionField
     * @return
     */
    public static String referencedProcess(ProjectionData projectionData,
            ProjectionField projectionField, Element element, Boolean typeReferencedEmbedded) {
        String result = "";
        String projectionNameUpper = JmoordbCoreUtil.letterToUpper(projectionData.getProjectionName());
        String projectionNameLower = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfProjectionFile(element, fieldUpper + ".java", idData);

        String as = projectionField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = projectionField.getViewReferenced().from();
        String localField = projectionField.getViewReferenced().localField();
        String sourceSupplier = "";
        if (typeReferencedEmbedded) {
            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + projectionNameLower + ".get" + fieldUpper + "())" + ");\n";
        } else {
            sourceSupplier = "\t\tdocument_.put(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + projectionNameLower + ".get" + fieldUpper + "())" + ");\n";
        }

        try {

            if (projectionField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (projectionField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (projectionField.getReturnTypeValue().contains("Stream")) {
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



    // <editor-fold defaultstate="collapsed" desc="String referencedProcessUpdate(ProjectionData projectionData, ProjectionField projectionField String caracterComa,String caracterComa,Boolean typeReferencedEmbedded)">
    /**
     * Procesa los documentos Referenciados
     *
     * @param projectionData
     * @param projectionField
     * @return
     */
    public static String toUpdate(ProjectionData projectionData, ProjectionField projectionField, Element element, String caracterComa, Boolean typeReferencedEmbedded) {
        String result = "";
        String projectionNameUpper = JmoordbCoreUtil.letterToUpper(projectionData.getProjectionName());
        String projectionNameLower = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName());
        String fieldUpper = JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod());
        String fieldLower = JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod());

        IdData idData = new IdData();
        JmoordbCoreFileUtil.readIdAnnotationOfProjectionFile(element, fieldUpper + ".java", idData);

        String as = projectionField.getViewReferenced().from();

        String foreignField = idData.getFieldName();
        String from = projectionField.getViewReferenced().from();
        String localField = projectionField.getViewReferenced().localField();
        /**
         * Para listas
         */
//        String sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + ".$[]\"," + fieldLower + "Supplier.toDocument(" + projectionNameLower + ".get" + fieldUpper + "())" + ")"+ caracterComa+"\n";
 String sourceSupplier = "";
        if (typeReferencedEmbedded) {
            
             sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toDocument(" + projectionNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        }else{
            

//             sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + projectionNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
             sourceSupplier = "\t\tUpdates.set(\"" + projectionData.getProjectionName() + "\"," + fieldLower + "Supplier.toReferenced(" + projectionNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
        }
        
        try {

            if (projectionField.getReturnTypeValue().contains("List")) {

                result += "\t// Referenced List<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (projectionField.getReturnTypeValue().contains("Set")) {
                result += "\t// Referenced Set<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }
            if (projectionField.getReturnTypeValue().contains("Stream")) {
                result += "\t// Referenced Stream<" + fieldLower + ">\n";

                result += sourceSupplier;
                return result;
            }

            /**
             * Referencias simples
             */
              if (typeReferencedEmbedded) {
            sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toUpdate(" + projectionNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
              }else{

                sourceSupplier = "\t\tUpdates.set(\"" + fieldLower + "\"," + fieldLower + "Supplier.toReferenced(" + projectionNameLower + ".get" + fieldUpper + "())" + ")" + caracterComa + "\n";
            }
            result += "\t// Referenced of " + fieldLower + "\n";

            result += sourceSupplier;

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>


}
