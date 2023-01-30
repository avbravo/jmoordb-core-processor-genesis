package com.jmoordb.core.processor.viewentity.supplier;

import com.jmoordb.core.annotation.enumerations.ReturnType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import static com.jmoordb.core.processor.builder.interfaces.SupplierCastConverterBuilder.castConverter;
import com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedGetBuilder;
import com.jmoordb.core.processor.builder.interfaces.SupplierReferencedGetBuilder;

import com.jmoordb.core.processor.methods.ViewEntityField;
import com.jmoordb.core.processor.model.ViewEntityData;
import com.jmoordb.core.processor.viewentity.supplier.ViewEntitySupplierSourceUtil;
import com.jmoordb.core.processor.viewentity.supplier.generate.ViewEntitySupplierGenerateToDocument;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;


public class ViewEntitySupplier  implements ViewEntitySupplierGenerateToDocument{

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder get(ViewEntityData viewViewEntityData, List<ViewEntityField> viewViewEntityFieldList, Element element)">
    public static StringBuilder get(ViewEntityData viewViewEntityData, List<ViewEntityField> viewViewEntityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = ViewEntitySupplierSourceUtil.haveEmbedded(viewViewEntityFieldList);
            Boolean haveReferenced = ViewEntitySupplierSourceUtil.haveReferenced(viewViewEntityFieldList);

            String sentence = "\t";
            String cast = "";
            for (ViewEntityField viewViewEntityField : viewViewEntityFieldList) {
                switch (viewViewEntityField.getAnnotationType()) {
                    case EMBEDDED:
                        sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(viewViewEntityData, viewViewEntityField);
                        break;
                    case REFERENCED:

                        if (viewViewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {

//                            sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(viewViewEntityData, viewViewEntityField);
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(viewViewEntityData, viewViewEntityField, element);
                        } else {
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(viewViewEntityData, viewViewEntityField, element);
                        }

                        break;
                        
		
                    case VIEWREFERENCED:

                        if (viewViewEntityField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {


                            sentence += SupplierReferencedGetBuilder.viewReferencedProcessGet(viewViewEntityData, viewViewEntityField, element);
                        } else {
                            sentence += SupplierReferencedGetBuilder.viewReferencedProcessGet(viewViewEntityData, viewViewEntityField, element);
                        }
                        break;
                    case ID:
                        cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());
                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod()) + "(" + cast + ");\n";
                        break;
                    case COLUMN:
                        if (viewViewEntityField.getReturnType().equals(ReturnType.DATE)) {
                            cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());
                            sentence += "\t" + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod()) + "(" + cast + ");\n";
                        } else {
                            cast = castConverter(viewViewEntityField.getReturnTypeValue(), viewViewEntityField.getNameOfMethod());
                            sentence += "\t" + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + ".set" + JmoordbCoreUtil.letterToUpper(viewViewEntityField.getNameOfMethod()) + "(" + cast + ");\n";
                        }

                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFold(viewViewEntityData) + "\n\n"
                    + "    public " + viewViewEntityData.getEntityName() + " get(Supplier<? extends " + viewViewEntityData.getEntityName() + "> s, Document document_) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(viewViewEntityData.getEntityName()) + " " + JmoordbCoreUtil.letterToLower(viewViewEntityData.getEntityName()) + "= s.get(); \n"
                    + "        try {\n"
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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