/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.entity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.processor.entity.supplier.EntitySupplierBuilderUtil;
import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.entity.supplier.generate.util.EntitySupplierReferencedUtil;
import com.jmoordb.core.processor.entity.supplier.generate.util.EntitySupplierViewReferencedUtil;
import com.jmoordb.core.processor.entity.supplier.embedded.EntitySupplierEmbeddedBuilder;

/**
 *
 * @author avbravo
 */
public interface EntitySupplierGenerateToUpdate {

    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toUpdate(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);

            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\n";
            String caracterComa = ",";
            for (EntityField entityField : entityFieldList) {
                if ((count + 1) == entityFieldList.size()) {
                    caracterComa = "";

                }

                switch (entityField.getAnnotationType()) {

                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + EntitySupplierEmbeddedBuilder.toUpdate(entityData, entityField, caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += " " + coma + EntitySupplierReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.TRUE);
                        } else {

                            sentence += " " + coma + EntitySupplierReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + EntitySupplierViewReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.TRUE);
                        } else {
                            sentence += " " + coma + EntitySupplierViewReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                          String sentenceToString="";
                         if (entityField.getReturnType().equals(ReturnType.UUID)) {
                             sentenceToString=".toString()";
                             
                         }
                        if (entityField.getNameOfMethod().toLowerCase().trim().equals("_id")) {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()"+sentenceToString;
                            sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";
                        } else {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()"+sentenceToString;
                            sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";
                        }

                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                   
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()" ;
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdate(entityData) + "\n\n"
                    + "    public Bson toUpdate(" + entityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ") {\n"
                    + "        Bson update_ = Filters.empty();\n"
                    + "        try {\n"
                    + "        update_ = Updates.combine(\n"
                    + sentence + "\n"
                    + "        );" + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return update_;\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateList(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toUpdateList(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);

            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
            String lower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());

            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
            String caracterComa = ",";
            for (EntityField entityField : entityFieldList) {
                if ((count + 1) == entityFieldList.size()) {
                    caracterComa = "";

                }
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + EntitySupplierEmbeddedBuilder.toUpdate(entityData, entityField, caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {

                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + EntitySupplierReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.TRUE);
                        } else {
                            sentence += " " + coma + EntitySupplierReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {

                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += " " + coma + EntitySupplierViewReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.TRUE);
                        } else {

                            sentence += " " + coma + EntitySupplierViewReferencedUtil.toUpdate(entityData, entityField, element, caracterComa, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        String sentenceToString = "";
                        if (entityField.getReturnType().equals(ReturnType.UUID)) {
                            sentenceToString = ".toString()";

                        }
                        if (entityField.getNameOfMethod().toLowerCase().trim().equals("_id")) {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()" + sentenceToString;
                            sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";
                        } else {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()" + sentenceToString;
                            sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";
                        }

                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                       
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")" + caracterComa + "\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateList(entityData) + "\n\n"
                    + "    public List<Bson> toUpdate(List<" + entityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + "List) {\n"
                    + "        List<Bson> bsonList_ = new ArrayList<>();\n"
                    + "        try {\n"
                    + "\t for(" + upper + " " + lower + " : " + lower + "List){\n"
                    + "\t\t Bson update_ = Filters.empty();\n"
                    + "\t\t\tupdate_ = Updates.combine(\n"
                    + sentence + "\n"
                    + "        );" + "\n"
                    + "\t\tbsonList_.add(update_);\n " + "\n"
                    + "       }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return bsonList_;\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
