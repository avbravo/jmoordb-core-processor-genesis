/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.entity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.processor.entity.model.EntityData;
import com.jmoordb.core.processor.entity.supplier.EntitySupplierBuilderUtil;
import com.jmoordb.core.processor.fields.EntityField;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface EntitySupplierGenerateToReferenced {

    // <editor-fold defaultstate="collapsed" desc="StringBuilder toReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);

            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {

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
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        } else {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()"+sentenceToString;
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        }

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferenced(entityData) + "\n\n"
                    + "    public Document toReferenced(" + entityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ") {\n"
                    + "        Document document_ = new Document();\n"
                    + "        try {\n"
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return document_;\n"
                    + "     }\n"
                    + "// </editor-fold>\n";

            builder.append(code);

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toRefernecedList(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toReferencedList(EntityData entityData, List<EntityField> entityFieldList, Element element) {
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

            sentence += "\t for(" + upper + " " + lower + " : " + lower + "List){\n";
            sentence += "\t\t Document document_ = new Document();\n";
            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {

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
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        } else {
                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()"+sentenceToString;
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        }

                        count++;
                        break;

                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";
//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferencedList(entityData) + "\n\n"
                    + "    public List<Document> toReferenced(List<" + entityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + "List) {\n"
                    + "        List<Document> documentList_ = new ArrayList<>();\n"
                    + "        try {\n"
                    + sentence + "\n"
                    + "       }\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return documentList_;\n"
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
