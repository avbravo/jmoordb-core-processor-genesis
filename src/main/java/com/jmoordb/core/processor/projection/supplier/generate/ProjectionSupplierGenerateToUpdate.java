/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.projection.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedBuilder;
import com.jmoordb.core.processor.projection.supplier.ProjectionSupplierSourceUtil;
import com.jmoordb.core.processor.methods.ProjectionField;
import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.projection.supplier.generate.util.ProjectionSupplierReferencedUtil;

/**
 *
 * @author avbravo
 */
public interface ProjectionSupplierGenerateToUpdate {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdate(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element)">
    public static StringBuilder toUpdate(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ProjectionSupplierSourceUtil.haveEmbedded(projectionFieldList);
            Boolean haveReferenced = ProjectionSupplierSourceUtil.haveReferenced(projectionFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\n";
            String caracterComa=",";
            for (ProjectionField projectionField : projectionFieldList) {
                 if((count +1)== projectionFieldList.size()){
                     caracterComa="";
                    
                 }
                 
                switch (projectionField.getAnnotationType()) {
                   
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.toUpdate(projectionData, projectionField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        if (projectionField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
                            
                           // sentence += SupplierEmbeddedBuilder.toUpdate(projectionData, projectionField,caracterComa);
//                           sentence += " " + coma + SupplierReferencedBuilder.toUpdate(projectionData, projectionField, element,caracterComa);
                           sentence += " " + coma + ProjectionSupplierReferencedUtil.toUpdate(projectionData, projectionField, element,caracterComa,Boolean.TRUE);
                        } else {
                           
//                            sentence += " " + coma + SupplierReferencedBuilder.toUpdate(projectionData, projectionField, element,caracterComa);
                            sentence += " " + coma + ProjectionSupplierReferencedUtil.toUpdate(projectionData, projectionField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdate(projectionData) + "\n\n"
                    + "    public Bson toUpdate(" + projectionData.getProjectionName() + " " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ") {\n"
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
   
    
    
    
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateList(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element)">
    public static StringBuilder toUpdateList(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element) {
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

        
      
            String cast = "";
            String getMethod = "";
            Integer count = 0;
//            String coma = "\\n \\\"";
            String coma = "\n ";
                String caracterComa=",";
            for (ProjectionField projectionField : projectionFieldList) {
                 if((count + 1) == projectionFieldList.size()){
                     caracterComa="";
                   
                 }
                switch (projectionField.getAnnotationType()) {
                    case EMBEDDED:
                        if (count > 0) {
                            coma = "\n";
                        }
                        sentence += coma + SupplierEmbeddedBuilder.toUpdate(projectionData, projectionField,caracterComa);
                        count++;
                        break;
                    case REFERENCED:
                        if (count > 0) {
//                            coma = "\n, \"";
                            coma = "\n";
                        }
                        if (projectionField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

                          //  sentence += SupplierEmbeddedBuilder.toUpdate(projectionData, projectionField,caracterComa);
//                          sentence += " " + coma + SupplierReferencedBuilder.toUpdate(projectionData, projectionField, element,caracterComa);
                          sentence += " " + coma + ProjectionSupplierReferencedUtil.toUpdate(projectionData, projectionField, element,caracterComa,Boolean.TRUE);
                        } else {
                            
//                            sentence += " " + coma + SupplierReferencedBuilder.toUpdate(projectionData, projectionField, element,caracterComa);
                            sentence += " " + coma + ProjectionSupplierReferencedUtil.toUpdate(projectionData, projectionField, element,caracterComa,Boolean.FALSE);
                        }
                        count++;
                        break;
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";
                        count++;
                        break;
                    case COLUMN:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".get" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(projectionField.getNameOfMethod()) + "\"," + getMethod + ")"+ caracterComa+"\n";

                        count++;
                        break;

                }

            }


            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateList(projectionData) + "\n\n"
                    + "    public List<Bson> toUpdate(List<" + projectionData.getProjectionName() + "> " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + "List) {\n"
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
