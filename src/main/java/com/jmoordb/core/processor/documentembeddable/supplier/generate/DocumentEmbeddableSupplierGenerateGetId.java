package com.jmoordb.core.processor.documentembeddable.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.processor.builder.castconverter.SupplierCastConverterBuilder.castConverter;

import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.processor.documentembeddable.supplier.DocumentEmbeddableSupplierBuilderUtil;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

public class DocumentEmbeddableSupplierGenerateGetId {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder getId(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder getId(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierBuilderUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierBuilderUtil.haveReferenced(documentEmbeddableFieldList);
            Boolean haveViewReferenced = DocumentEmbeddableSupplierBuilderUtil.haveViewReferenced(documentEmbeddableFieldList);

            String sentence = "\t";
            String cast = "";
            for (DocumentEmbeddableField documentEmbeddableField : documentEmbeddableFieldList) {
                switch (documentEmbeddableField.getAnnotationType()) {
                    
                    case ID:
                         if (documentEmbeddableField.getNameOfMethod().toLowerCase().trim().equals("_id")) {
                              cast = castConverter(documentEmbeddableField.getReturnTypeValue(), documentEmbeddableField.getNameOfMethod());
//                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".set" + JmoordbCoreUtil.letterToUpper(documentEmbeddableField.getNameOfMethod()) + "(new ObjectId(" + cast + "));\n";
                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".set" + JmoordbCoreUtil.letterToUpper(documentEmbeddableField.getNameOfMethod()) + "(" + cast + ");\n";
                         }else{
                              cast = castConverter(documentEmbeddableField.getReturnTypeValue(), documentEmbeddableField.getNameOfMethod());
                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".set" + JmoordbCoreUtil.letterToUpper(documentEmbeddableField.getNameOfMethod()) + "(" + cast + ");\n";
                         }
                       
                        break;
                   

                }

            }

            String code
                    = ProcessorUtil.editorFold(documentEmbeddableData) + "\n\n"
                    + "    public " + documentEmbeddableData.getDocumentEmbeddableName() + " getId(Supplier<? extends " + documentEmbeddableData.getDocumentEmbeddableName() + "> s, Document document_, Boolean... showError) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName()) + " " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + "= s.get(); \n"
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
                    + "         return " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ";\n"
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
