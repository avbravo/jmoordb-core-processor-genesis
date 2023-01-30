package com.jmoordb.core.processor.documentembeddable.supplier;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.COLUMN;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.EMBEDDED;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import static com.jmoordb.core.annotation.enumerations.AnnotationType.REFERENCED;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import static com.jmoordb.core.processor.builder.interfaces.SupplierCastConverterBuilder.castConverter;
import com.jmoordb.core.processor.builder.interfaces.SupplierEmbeddedGetBuilder;
import com.jmoordb.core.processor.builder.interfaces.SupplierReferencedGetBuilder;

import com.jmoordb.core.processor.methods.DocumentEmbeddableField;
import com.jmoordb.core.processor.documentembeddable.model.DocumentEmbeddableData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

public class DocumentEmbeddableSupplier {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder get(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element)">
    public static StringBuilder get(DocumentEmbeddableData documentEmbeddableData, List<DocumentEmbeddableField> documentEmbeddableFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = DocumentEmbeddableSupplierSourceUtil.haveEmbedded(documentEmbeddableFieldList);
            Boolean haveReferenced = DocumentEmbeddableSupplierSourceUtil.haveReferenced(documentEmbeddableFieldList);
            Boolean haveViewReferenced = DocumentEmbeddableSupplierSourceUtil.haveViewReferenced(documentEmbeddableFieldList);

            String sentence = "\t";
            String cast = "";
            for (DocumentEmbeddableField documentEmbeddableField : documentEmbeddableFieldList) {
                switch (documentEmbeddableField.getAnnotationType()) {
                    case EMBEDDED:
                        sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, documentEmbeddableField);
                        break;
                    case REFERENCED:

                        if (documentEmbeddableField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
 /**
  * Anteriromente lo leia como embebido
  **/
 //sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, documentEmbeddableField);
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(documentEmbeddableData, documentEmbeddableField, element);
                        } else {
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(documentEmbeddableData, documentEmbeddableField, element);
                        }

                        break;
                    case VIEWREFERENCED:

                        if (documentEmbeddableField.getTypeReferenced().equals(TypeReferenced.EMBEDDED)) {
 /**
  * Anteriromente lo leia como embebido
  **/
 //sentence += SupplierEmbeddedGetBuilder.embeddedProcessGet(documentEmbeddableData, documentEmbeddableField);
                            sentence += SupplierReferencedGetBuilder.referencedProcessGet(documentEmbeddableData, documentEmbeddableField, element);
                        } else {
                            sentence += SupplierReferencedGetBuilder.viewReferencedProcessGet(documentEmbeddableData, documentEmbeddableField, element);
                        }

                        break;
                    case ID:
                        cast = castConverter(documentEmbeddableField.getReturnTypeValue(), documentEmbeddableField.getNameOfMethod());
                        sentence += "\n\t " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".set" + JmoordbCoreUtil.letterToUpper(documentEmbeddableField.getNameOfMethod()) + "(" + cast + ");\n";
                        break;
                    case COLUMN:
                        cast = castConverter(documentEmbeddableField.getReturnTypeValue(), documentEmbeddableField.getNameOfMethod());
                        sentence += "\t" + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + ".set" + JmoordbCoreUtil.letterToUpper(documentEmbeddableField.getNameOfMethod()) + "(" + cast + ");\n";
                        break;

                }

            }

            String code
                    = ProcessorUtil.editorFold(documentEmbeddableData) + "\n\n"
                    + "    public " + documentEmbeddableData.getDocumentEmbeddableName() + " get(Supplier<? extends " + documentEmbeddableData.getDocumentEmbeddableName() + "> s, Document document_) {\n"
                    + "        " + JmoordbCoreUtil.letterToUpper(documentEmbeddableData.getDocumentEmbeddableName()) + " " + JmoordbCoreUtil.letterToLower(documentEmbeddableData.getDocumentEmbeddableName()) + "= s.get(); \n"
                    + "        try {\n"
                    
                    + sentence + "\n"
                    + "         } catch (Exception e) {\n"
                    + "              MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());\n"
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
