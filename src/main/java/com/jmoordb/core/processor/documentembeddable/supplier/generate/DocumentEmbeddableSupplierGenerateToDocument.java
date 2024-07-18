/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.documentembeddable.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.documentembeddable.supplier.DocumentEmbeddableSupplierBuilderUtil;
import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.documentembeddable.supplier.embedded.DocumentEmbeddableSupplierEmbeddedBuilder;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.util.DocumentEmbeddableSupplierReferencedUtil;
import com.jmoordb.core.processor.documentembeddable.supplier.generate.util.DocumentEmbeddableSupplierViewReferencedUtil;
import com.jmoordb.core.util.ConsoleUtil;

/**
 *
 * @author avbravo
 */
public interface DocumentEmbeddableSupplierGenerateToDocument {
   
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocument(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder toDocument(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);

//            String sentence = "\t Document document = new Document();\n";
//            String sentence = "\t \n";
            String sentence = "\t ";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\n ";
            for (DocumentEmbeddableField entityField : documentEmbeddableFieldList) {
                switch (entityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
//                            coma = "\n,";
                            coma = "\n";
                        }
                        sentence += coma + DocumentEmbeddableSupplierEmbeddedBuilder.embeddedProcess(documentEmbeddableData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                           sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.TRUE);
                        } else {
                            sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.referencedProcess(documentEmbeddableData, entityField,element, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        
                        
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                           sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.TRUE);
                        } else {
                            sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.referencedProcess(documentEmbeddableData, entityField,element, Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
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
            Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";

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
                        sentence += coma + DocumentEmbeddableSupplierEmbeddedBuilder.embeddedProcess(documentEmbeddableData, entityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                           
                           sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.TRUE);
                        } else {
                           

                            sentence += " " + coma + DocumentEmbeddableSupplierReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (entityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                           

                           sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.TRUE);
                        } else {
                           

                            sentence += " " + coma + DocumentEmbeddableSupplierViewReferencedUtil.referencedProcess(documentEmbeddableData, entityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "()";
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
   
    
}
