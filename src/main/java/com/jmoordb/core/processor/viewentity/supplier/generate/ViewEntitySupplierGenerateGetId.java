package com.jmoordb.core.processor.viewentity.supplier.generate;

import static com.jmoordb.core.processor.builder.castconverter.SupplierCastConverterBuilder.castConverter;

import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierBuilderUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

public class ViewEntitySupplierGenerateGetId implements ViewEntitySupplierGenerateToDocument {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder getID(ViewEntityData viewViewEntityData, List<ViewEntityField> viewViewEntityFieldList, Element element)">
    public static StringBuilder getId(ViewEntityData viewViewEntityData, List<ViewEntityField> viewViewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierBuilderUtil.haveEmbedded(viewViewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierBuilderUtil.haveReferenced(viewViewEntityFieldList);

            String sentence = "\t";
            String cast = "";
            for (ViewEntityField viewViewEntityField : viewViewEntityFieldList) {
                switch (viewViewEntityField.getAnnotationType()) {

                    case ID:
                        if (viewViewEntityField.getNameOfMethod().toLowerCase().trim().equals("_id")) {
                            cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());

                            sentence += "\n\t " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(JmoordbCoreUtil.rename_IdToId(viewViewEntityField.getNameOfMethod())) + "(" + cast + ");\n";
                        } else {
                               if (viewViewEntityField.getReturnTypeValue().equals("java.util.UUID")) {
                                    cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());
                            sentence += "\n\t " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod()) + "(UUID.fromString(" + cast + "));\n";
                               }else{
                                     cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());
                            sentence += "\n\t " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod()) + "(" + cast + ");\n";
                               }
                          
                        }

                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFoldId(viewViewEntityData) + "\n\n"
                    + "    public " + viewViewEntityData.getEntityName() + " getId(Supplier<? extends " + viewViewEntityData.getEntityName() + "> s, Document document_, Boolean... showError) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(viewViewEntityData.getEntityName()) + " " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + "= s.get(); \n"
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
                    + "         return " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ";\n"
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
