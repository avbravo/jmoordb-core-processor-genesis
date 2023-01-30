/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.processor.model;

import com.jmoordb.core.annotation.ViewEntity;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.function.Supplier;
import javax.lang.model.element.Element;

/**I
 *
 * @author avbravo
 */
public class ViewEntityDataSupplier {
// <editor-fold defaultstate="collapsed" desc="ViewEntityData get(Supplier<? extends ViewEntityData> s, Element element)">

    public ViewEntityData get(Supplier<? extends ViewEntityData> s, Element element) {
        ViewEntityData entityData = s.get();
        try {

            ViewEntity entity = element.getAnnotation(ViewEntity.class);
    
            String database=   "{mongodb.database}";    
            if (entity.database() == null  || entity.database().equals("")) {
                database = "{mongodb.database}";
          
            } else {
     
                database = entity.database().replace("{", "").replace("}", "");
            }
            String collection ="";
    
           if (entity.collection() == null ||  entity.collection().trim().equals("")){
                    collection =JmoordbCoreUtil.letterToLower(ProcessorUtil.getTypeName(element));
                
            }else{
               collection = entity.collection();
           }
          
            entityData = new ViewEntityData.Builder()
                    .collection(collection)
                    .jakartaSource(entity.jakartaSource())
                    .database(database)                    
                    .packageOfViewEntity(ProcessorUtil.getPackageName(element))
                     .entityName(ProcessorUtil.getTypeName(element))
                    .build();
            
            

        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return entityData;
    }
    // </editor-fold>

}
