/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.builder.interfaces;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.builder.DocumentEmbeddableSupplierSourceBuilderUtil;
import com.jmoordb.core.processor.builder.SupplierSourceBuilderUtil;
import static com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedBuilder.embeddedProcess;
import static com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedBuilder.embeddedProcessUpdate;
import static com.jmoordb.core.processor.builder.interfaces.SupplierReferencedBuilder.referencedProcess;
import static com.jmoordb.core.processor.builder.interfaces.SupplierReferencedBuilder.referencedProcessUpdate;
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.methods.EntityField;
import com.jmoordb.core.processor.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface SupplierToDocumentBuilder {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocument(EntityData entityData, List<EntityField> entityFieldList, Element element)">

    public static StringBuilder toDocument(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = SupplierSourceBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = SupplierSourceBuilderUtil.haveReferenced(entityFieldList);

//            String sentence = "\t Document document = new Document();\n";
            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += SupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        } else {
                            //   sentence += "+// Embedded of" + coma + referencedProcess(entityData, entityField, element);
                            sentence += " " + coma + referencedProcess(entityData, entityField, element);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
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
            Boolean haveEmbedded = SupplierSourceBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = SupplierSourceBuilderUtil.haveReferenced(entityFieldList);

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
            String coma = "\\n \\\"";
            for (EntityField entityField : entityFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += SupplierEmbeddedBuilder.embeddedProcess(entityData, entityField);
                        } else {
                            //   sentence += "+// Embedded of" + coma + referencedProcess(entityData, entityField, element);
                            sentence += " " + coma + referencedProcess(entityData, entityField, element);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocument(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toDocument(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierSourceBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierSourceBuilderUtil.haveReferenced(documentEmbeddableFieldList);

//            String sentence = "\t Document document = new Document();\n";
            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + embeddedProcess(documentEmbeddableData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += embeddedProcess(documentEmbeddableData, entityField);
                        } else {
                            //   sentence += "+// Embedded of" + coma + referencedProcess(entityData, entityField, element);
                            sentence += " " + coma + referencedProcess(documentEmbeddableData, entityField, element);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }

//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocument(documentEmbeddableData) + "\n\n"
                    + "    public Document toDocument(" + documentEmbeddableData.getDocumentEmbeddableName() + " " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ") {\n"
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocumentList(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toDocumentList(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierSourceBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierSourceBuilderUtil.haveReferenced(documentEmbeddableFieldList);

//            String sentence = "\t Document document = new Document();\n";
            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName());
            String lower = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName());

            sentence += "\t for(" + upper + " " + lower + " : " + lower + "List){\n";
            sentence += "\t\t Document document_ = new Document();\n";

            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + embeddedProcess(documentEmbeddableData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += embeddedProcess(documentEmbeddableData, entityField);
                        } else {
                            //   sentence += "+// Embedded of" + coma + referencedProcess(entityData, entityField, element);
                            sentence += " " + coma + referencedProcess(documentEmbeddableData, entityField, element);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";
//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocumentList(documentEmbeddableData) + "\n\n"
                    + "    public List<Document> toDocument(List<" + documentEmbeddableData.getDocumentEmbeddableName() + "> " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + "List) {\n"
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toUpdate(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = SupplierSourceBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = SupplierSourceBuilderUtil.haveReferenced(entityFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
            String caracterComa=",";
            for (EntityField entityField : entityFieldList) {
                 if((count +1)== entityFieldList.size()){
                     caracterComa="";
                    
                 }
                 
                switch (entityField.getAnnotationType()) {
                   
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.embeddedProcessUpdate(entityData, entityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += SupplierEmbeddedBuilder.embeddedProcessUpdate(entityData, entityField,caracterComa);
                        } else {
                           
                            sentence += " " + coma + referencedProcessUpdate(entityData, entityField, element,caracterComa);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toUpdate(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierSourceBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierSourceBuilderUtil.haveReferenced(documentEmbeddableFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
                 String caracterComa=",";
            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                   if((count + 1) == documentEmbeddableFieldList.size()){
                     caracterComa="";
                   
                 }
                 
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + embeddedProcessUpdate(documentEmbeddableData, entityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += embeddedProcessUpdate(documentEmbeddableData, entityField,caracterComa);
                        } else {
                            //   sentence += "+// Embedded of" + coma + referencedProcess(entityData, entityField, element);
                            sentence += " " + coma + referencedProcessUpdate(documentEmbeddableData, entityField, element,caracterComa);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }

//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdate(documentEmbeddableData) + "\n\n"
                    + "    public Bson toUpdate(" + documentEmbeddableData.getDocumentEmbeddableName() + " " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ") {\n"
                    + "       Bson update_ = Filters.empty();\n"
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
            Boolean haveEmbedded = SupplierSourceBuilderUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = SupplierSourceBuilderUtil.haveReferenced(entityFieldList);


            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
            String lower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());

        
      
            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\\n \\\"";
                String caracterComa=",";
            for (EntityField entityField : entityFieldList) {
                 if((count + 1) == entityFieldList.size()){
                     caracterComa="";
                   
                 }
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.embeddedProcessUpdate(entityData, entityField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += SupplierEmbeddedBuilder.embeddedProcessUpdate(entityData, entityField,caracterComa);
                        } else {
                            
                            sentence += " " + coma + referencedProcessUpdate(entityData, entityField, element,caracterComa);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

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
                    + "\t\tbsonList_.add(update_);\n "+ "\n"
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
