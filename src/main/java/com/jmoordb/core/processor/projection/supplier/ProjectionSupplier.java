package com.jmoordb.core.processor.projection.supplier;

import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import static com.jmoordb.core.processor.builder.interfaces.SupplierCastConverterBuilder.castConverter;
import com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedGetBuilder;
import com.jmoordb.core.processor.builder.interfaces.SupplierReferencedGetBuilder;

import com.jmoordb.core.processor.methods.ProjectionField;
import com.jmoordb.core.processor.model.ProjectionData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.projection.supplier.generate.ProjectionSupplierGenerateToDocument;

public class ProjectionSupplier  implements ProjectionSupplierGenerateToDocument{

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder get(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element)">
    public static StringBuilder get(ProjectionData projectionData, List<ProjectionField> projectionFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ProjectionSupplierSourceUtil.haveEmbedded(projectionFieldList);
            Boolean haveReferenced = ProjectionSupplierSourceUtil.haveReferenced(projectionFieldList);

            String sentence = "\t";
            String cast = "";
            for (ProjectionField projectionField : projectionFieldList) {
                switch (projectionField.getAnnotationType()) {
                    case EMBEDDED:
                       sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(projectionData, projectionField);
                        break;
                    case REFERENCED:

                        if (projectionField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

//                            sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(projectionData, projectionField);
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(projectionData, projectionField, element);
                        } else {
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(projectionData, projectionField, element);
                        }

                        break;
                    case ID:
                        cast = castConverter(projectionField.getReturnTypeValue(), projectionField.getNameOfMethod());
                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".set" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "(" + cast + ");\n";
                        break;
                    case COLUMN:
                        if (projectionField.getReturnType().equals(ReturnType.DATE)) {
                            cast = castConverter(projectionField.getReturnTypeValue(), projectionField.getNameOfMethod());
                            sentence += "\t" + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".set" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "(" + cast + ");\n";
                        } else {
                            cast = castConverter(projectionField.getReturnTypeValue(), projectionField.getNameOfMethod());
                            sentence += "\t" + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ".set" + JmoordbCoreUtil.letterToUpper(projectionField.getNameOfMethod()) + "(" + cast + ");\n";
                        }

                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFold(projectionData) + "\n\n"
                    + "    public " + projectionData.getProjectionName() + " get(Supplier<? extends " + projectionData.getProjectionName() + "> s, Document document_) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(projectionData.getProjectionName()) + " " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + "= s.get(); \n"
                    + "        try {\n"
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "         }\n"
                    + "         return " + JmoordbCoreUtil.letterToLower(projectionData.getProjectionName()) + ";\n"
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
