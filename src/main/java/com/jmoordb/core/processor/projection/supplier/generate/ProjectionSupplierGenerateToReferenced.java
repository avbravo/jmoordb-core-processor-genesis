/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.projection.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import com.jmoordb.core.processor.projection.supplier.ProjectionSupplierSourceUtil;
import com.jmoordb.core.processor.methods.ProjectionField;
import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface ProjectionSupplierGenerateToReferenced {
   
    
    
    
    
     // <editor-fold defaultstate="collapsed" desc="StringBuilder toReferenced(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element)">

    public static StringBuilder toReferenced(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ProjectionSupplierSourceUtil.haveEmbedded(projectionFieldList);
            Boolean haveReferenced = ProjectionSupplierSourceUtil.haveReferenced(projectionFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";
            for (ProjectionField projectionField : projectionFieldList) {
                switch (projectionField.getAnnotationType()) {

                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferenced(projectionData) + "\n\n"
                    + "    public Document toReferenced(" + projectionData.getProjectionName() + " " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ") {\n"
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
    
     // <editor-fold defaultstate="collapsed" desc="StringBuilder toRefernecedList(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element)">
    public static StringBuilder toReferencedList(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ProjectionSupplierSourceUtil.haveEmbedded(projectionFieldList);
            Boolean haveReferenced = ProjectionSupplierSourceUtil.haveReferenced(projectionFieldList);

            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(projectionData.getProjectionName());
            String lower = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName());

            sentence += "\t for(" + upper + " " + lower + " : " + lower + "List){\n";
            sentence += "\t\t Document document_ = new Document();\n";
            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
            for (ProjectionField projectionField : projectionFieldList) {
                switch (projectionField.getAnnotationType()) {
                  
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tdocument_.put(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ");\n";
                        count++;
                        break;
                   
                }

            }
            sentence += "\t\tdocumentList_.add(document_);\n";
//            sentence += "\treturn document;\n";
            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToReferencedList(projectionData) + "\n\n"
                    + "    public List<Document> toReferenced(List<" + projectionData.getProjectionName() + "> " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + "List) {\n"
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
