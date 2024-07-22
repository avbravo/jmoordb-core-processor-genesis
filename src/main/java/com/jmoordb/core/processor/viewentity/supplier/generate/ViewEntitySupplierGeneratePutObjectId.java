package com.jmoordb.core.processor.viewentity.supplier.generate;

import com.jmoordb.core.processor.entity.supplier.generate.*;
import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;
import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilderUtil;

public class ViewEntitySupplierGeneratePutObjectId implements EntitySupplierGenerateToDocument {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder putObjectId(ViewEntityData entityData, List<ViewEntityField> entityFieldList, Element element)">
    public static StringBuilder putObjectId(ViewEntityData viewEntityData, List<ViewEntityField> viewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewEntityFieldList);

            String sentence = "\t";
            String cast = "";
            for (ViewEntityField entityField : viewEntityFieldList) {
                switch (entityField.getAnnotationType()) {
                 
                    case COLUMN:

                       
                            if (entityField.getReturnType().equals(ReturnType.OBJECTID)) {
                                cast = "new ObjectId(_id)";
                                sentence += "\t" + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(entityField.getNameOfMethod())) + "(" + cast + ");\n";
                            } 
                     

                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFoldPutObjectId(viewEntityData) + "\n\n"
                    + "    public " + viewEntityData.getEntityName() + " putObjectId(" +viewEntityData.getEntityName() + " "+ JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName())+", String _id, Boolean... showError) {\n"
                   
                    + "            Boolean show = true;\n"
                    + "        try {\n"
                    + "            if (showError.length != 0) {\n"
                    + "                show = showError[0];\n"
                    + "            }\n"
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "             if (show) {\n"
                    + "                MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
                    + "             }\n"
                    + "         }\n"
                    + "         return " + JmoordbCoreUtil.letterToLower(viewEntityData.getEntityName()) + ";\n"
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
