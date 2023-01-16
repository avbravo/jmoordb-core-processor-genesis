/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jmoordb.core.processor.entity.supplier.generate;

import static com.jmoordb.core.annotation.enumerations.AnnotationType.ID;
import com.jmoordb.core.processor.entity.supplier.EntitySupplierSourceUtil;
import com.jmoordb.core.processor.methods.EntityField;
import com.jmoordb.core.processor.model.EntityData;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.List;
import javax.lang.model.element.Element;

/**
 *
 * @author avbravo
 */
public interface EntitySupplierGenerateToUpdateReferenced {
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toUpdateReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierSourceUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierSourceUtil.haveReferenced(entityFieldList);


            String sentence = "\t \n";

            String cast = "";
            String getMethod = "";
            Integer count = 0;
            String coma = "\n";

            for (EntityField entityField : entityFieldList) {
                
                 
                switch (entityField.getAnnotationType()) {
                   
                 
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+ "\n";
                        count++;
                        break;
                   

                }

            }

            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateReferenced(entityData) + "\n\n"
                    + "    public Bson toUpdateReferenced(" + entityData.getEntityName() + " " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ") {\n"
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
   
    
    
    
    // <editor-fold defaultstate="collapsed" desc="StringBuilder toUpdateListReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element)">
    public static StringBuilder toUpdateListReferenced(EntityData entityData, List<EntityField> entityFieldList, Element element) {
        StringBuilder builder = new StringBuilder();
        try {
            Boolean haveEmbedded = EntitySupplierSourceUtil.haveEmbedded(entityFieldList);
            Boolean haveReferenced = EntitySupplierSourceUtil.haveReferenced(entityFieldList);


            String sentence = "\t \n";

            /**
             * for
             */
            String upper = JmoordbCoreUtil.letterToUpper(entityData.getEntityName());
            String lower = JmoordbCoreUtil.letterToLower(entityData.getEntityName());

        
      
            String cast = "";
            String getMethod = "";
            Integer count = 0;

            String coma = "\n ";

            for (EntityField entityField : entityFieldList) {
                 
                switch (entityField.getAnnotationType()) {
                 
                    case ID:
                        if (count > 0) {
                            coma = "\\n, \\\"";
                        }
                        getMethod = JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + ".get" + JmoordbCoreUtil.letterToUpper(entityField.getNameOfMethod()) + "()";
                        sentence += "\t\tUpdates.set(\"" + JmoordbCoreUtil.letterToLower(entityField.getNameOfMethod()) + "\"," + getMethod + ")"+"\n";
                        count++;
                        break;
                 

                }

            }


            sentence += "\t\n";
            String code
                    = ProcessorUtil.editorFoldToUpdateListReferenced(entityData) + "\n\n"
                    + "    public List<Bson> toUpdateReferenced(List<" + entityData.getEntityName() + "> " + JmoordbCoreUtil.letterToLower(entityData.getEntityName()) + "List) {\n"
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
