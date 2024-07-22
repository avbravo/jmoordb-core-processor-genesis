/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.viewentity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilderUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface ViewEntitySupplierGenerateToReferenced {
   
    
    
    
    
     // <editor-fold defaultstate="collapsed" desc="StringBuilder toReferenced(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">

    public static StringBuilder toReferenced(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
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

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferenced(viewEntityData) + "\n\n"
                    + "    public Document toReferenced(" + viewEntityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ") {\n"
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
    
     // <editor-fold defaultstate="collapsed" desc="StringBuilder toRefernecedList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element)">
    public static StringBuilder toReferencedList(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);

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
                   
                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferencedList(viewEntityData) + "\n\n"
                    + "    public List<Document> toReferenced(List<" + viewEntityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + "List) {\n"
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
