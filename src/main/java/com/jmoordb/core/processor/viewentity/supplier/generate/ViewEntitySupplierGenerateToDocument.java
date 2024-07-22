/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilderUtil;
import com.jmoordb.core.processor.viewentity.supplier.embedded.ViewEntitySupplierEmbeddedBuilder;
import com.jmoordb.core.processor.viewentity.supplier.generate.util.ViewEntitySupplierReferencedUtil;
import com.jmoordb.core.processor.viewentity.supplier.generate.util.ViewEntitySupplierViewReferencedUtil;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierGenerateToDocument {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocument(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">

    public static StringBuilder toDocument(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";
            for (ViewEntityField viewEntityField : viewEntityFieldList) {
                switch (viewEntityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + ViewEntitySupplierEmbeddedBuilder.embeddedProcess(viewEntityData, viewEntityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.referencedProcess(viewEntityData, viewEntityField, element,Boolean.TRUE);
                        } else {
                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.referencedProcess(viewEntityData, viewEntityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.viewReferencedProcess(viewEntityData, viewEntityField, element,Boolean.TRUE);
                        } else {
                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.viewReferencedProcess(viewEntityData, viewEntityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                        
                        
                        
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                           String sentenceToString="";
                         if (viewEntityField.getReturnType().equals(ReturnType.UUID)) {
                             sentenceToString=".toString()";
                             
                         }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()"+sentenceToString;
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocument(viewEntityData) + "\n\n"
                    + "    public Document toDocument(" + viewEntityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ") {\n"
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
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toDocumentList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">
    public static StringBuilder toDocumentList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);

//            String sentence = "\t Document document = new Document();\n";
            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(viewEntityData.getEntityName());
            String lower = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName());

            sentence += "\t for(" + upper + " " + lower + " : " + lower + "List){\n";
            sentence += "\t\t Document document_ = new Document();\n";
            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";
            for (ViewEntityField viewEntityField : viewEntityFieldList) {
                switch (viewEntityField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + ViewEntitySupplierEmbeddedBuilder.embeddedProcess(viewEntityData, viewEntityField);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.referencedProcess(viewEntityData, viewEntityField, element,Boolean.TRUE);
                        } else {

                            sentence += " " + coma + ViewEntitySupplierReferencedUtil.referencedProcess(viewEntityData, viewEntityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case VIEWREFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (viewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.viewReferencedProcess(viewEntityData, viewEntityField, element,Boolean.TRUE);
                        } else {

                            sentence += " " + coma + ViewEntitySupplierViewReferencedUtil.viewReferencedProcess(viewEntityData, viewEntityField, element,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                            String sentenceToString="";
                         if (viewEntityField.getReturnType().equals(ReturnType.UUID)) {
                             sentenceToString=".toString()";
                             
                         }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()"+sentenceToString;
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewEntityField.getNameOfMethod())) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(viewEntityField.getNameOfMethod()) + "\"," + getMethod + ");\n";

                        count++;
                        break;

                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToDocumentList(viewEntityData) + "\n\n"
                    + "    public List<Document> toDocument(List<" + viewEntityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + "List) {\n"
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
