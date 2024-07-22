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
public interface EntitySupplierGenerateToDocument {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocument(EntityData entityData, List<EntityField> entityFieldList, Element element)">

    public static StringBuilder toDocument(EntityData entityData, List<EntityField> entityFieldList, Element element) {
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
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + EntitySupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += " " + coma + EntitySupplierReferencedUtil.referencedProcess(entityData, entityField, element, Boolean.TRUE);
                        } else {

                            sentence += " " + coma + EntitySupplierReferencedUtil.referencedProcess(entityData, entityField, element, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + EntitySupplierViewReferencedUtil.viewReferencedProcess(entityData, entityField, element, Boolean.TRUE);
                        } else {

                            sentence += " " + coma + EntitySupplierViewReferencedUtil.viewReferencedProcess(entityData, entityField, element, Boolean.FALSE);
                        }
                        count++;
                        break;

                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        if(entityData.getEntityName().equals("Equipo")){
                            System.out.println("entityField.getReturnType():"+entityField.getReturnType());
                            if (entityField.getReturnType().equals(ReturnType.UUID)) {
                                System.out.println("\t {es UUID}");
                             
                         }else{
                                System.out.println("\t {no es UUID}");
                            }
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
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                      
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocument(entityData) + "\n\n"
                    + "    public Document toDocument(" + entityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ") {\n"
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocumentList(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toDocumentList(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierBuilderUtil.haveReferenced(entityFieldList);

//            String sentence = "\t Document document = new Document();\n";
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
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + EntitySupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {

                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += " " + coma + EntitySupplierReferencedUtil.referencedProcess(entityData, entityField, element, Boolean.TRUE);
                        } else {
                            sentence += " " + coma + EntitySupplierReferencedUtil.referencedProcess(entityData, entityField, element, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + EntitySupplierViewReferencedUtil.viewReferencedProcess(entityData, entityField, element, Boolean.TRUE);
                        } else {

                            sentence += " " + coma + EntitySupplierViewReferencedUtil.viewReferencedProcess(entityData, entityField, element, Boolean.FALSE);
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
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        } else {

                            getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()"+sentenceToString;
                            sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        }

                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                      
                        
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";
//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocumentList(entityData) + "\n\n"
                    + "    public List<Document> toDocument(List<" + entityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + "List) {\n"
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
