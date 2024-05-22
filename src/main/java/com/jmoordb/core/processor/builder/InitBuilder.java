package com.jmoordb.core.processor.builder;

import com.jmoordb.core.processor.model.RepositoryData;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;

public class InitBuilder {

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static String TAB = "   ";
    private String className;

    // <editor-fold defaultstate="collapsed" desc="StringBuilder updateMany(RepositoryData repositoryData, RepositoryMethod repositoryMethod)">
    public static StringBuilder init(RepositoryData repositoryData) {
        StringBuilder builder = new StringBuilder();
        try {

            String calculateReturn = "";
            String catchReturn = "";

            String code
                    = ProcessorUtil.editorFold("public void init()") + "\n\n";
            code +="""
                   
                @PostConstruct
                 public void init() {
                       try {

                         } catch (Exception e) {
                           MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                           exception = new JmoordbException(MessagesUtil.nameOfClassAndMethod() + \" \" + e.getLocalizedMessage());
                         }
                }
             // </editor-fold>
              """;
            builder.append(code);
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return builder;
    }

    // </editor-fold>
}
