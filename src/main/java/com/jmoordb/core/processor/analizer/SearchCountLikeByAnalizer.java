package com.jmoordb.core.processor.analizer;

import com.jmoordb.core.annotation.date.ExcludeTime;
import com.jmoordb.core.annotation.date.IncludeTime;
import com.jmoordb.core.annotation.repository.SearchCountLikeBy;
import com.jmoordb.core.processor.analizer.util.NameOfMethodAnalizerUtil;

import com.jmoordb.core.processor.fields.RepositoryMethod;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.jmoordb.core.util.MessagesUtil;
import com.jmoordb.core.util.ProcessorUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class SearchCountLikeByAnalizer {
// <editor-fold defaultstate="collapsed" desc="fields()">

    private static Integer MAXIMUM_NUMBER_OF_PARAMETERS = 2;
    private static Boolean NAMEOFIDENTIFIER_EQUALS_NAMEOFPARAMETERSOFMETHOD = Boolean.FALSE;
    static List<String> lexemas = new ArrayList<>();
    static List<String> worldAndToken = new ArrayList<>();

    private Messager messager;
    private static String message = "";

    public Messager getMessager() {
        return messager;
    }

    public void setMessager(Messager messager) {
        this.messager = messager;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        SearchCountLikeByAnalizer.message = message;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Boolean analizer(LikeBy likeByAnnotation, Element element, ExecutableElement executableElement, TypeMirror typeEntity, RepositoryMethod repositoryMethod)">

    public static Boolean analizer(SearchCountLikeBy searchCountLikeByAnnotation, Element element, ExecutableElement executableElement, TypeMirror typeEntity, RepositoryMethod repositoryMethod) {
        try {
            List<String> includeTimeFields = new ArrayList<>();
            List<String> excludeTimeFields = new ArrayList<>();
            String nameOfMethod = ProcessorUtil.nameOfMethod(executableElement);
            String nameOfEntity = ProcessorUtil.nameOfEntity(typeEntity);
            String typeOptional = "java.util.Optional<" + typeEntity.toString().trim() + ">";
            String typeList = "java.util.List<" + typeEntity.toString().trim() + ">";
            String typeSet = "java.util.Set<" + typeEntity.toString().trim() + ">";
            String typeStream = "java.util.stream.Stream<" + typeEntity.toString().trim() + ">";
            Boolean havePaginationParameters = Boolean.FALSE;
            Boolean haveSortedParameters = Boolean.FALSE;
            Boolean haveStringParameters = Boolean.FALSE;
            Integer countStringParameters = 0;
            /**
             *
             */

            TypeMirror returnTypeOfMethod = executableElement.getReturnType();

            if (!returnTypeOfMethod.toString().equals("java.lang.Long")) {
                message = nameOfMethod + "() The return type must be java.lang.Long";
                return Boolean.FALSE;
            }

            List<? extends VariableElement> parameters = executableElement.getParameters();

            Integer controlPaginationSorted = 0;
            if (parameters.size() <= 0) {
                message = nameOfMethod + "() Must have at least one String parameter";
                return Boolean.FALSE;
            } else {
                if (parameters.size() > MAXIMUM_NUMBER_OF_PARAMETERS) {
                    message = nameOfMethod + "() the maximum size of parameters allowed is " + MAXIMUM_NUMBER_OF_PARAMETERS + " . If you use more than" + MAXIMUM_NUMBER_OF_PARAMETERS + " use @QueryJSON instead.";
                    return Boolean.FALSE;
                }
                Boolean isIncludeTime = Boolean.FALSE;
                Boolean isExcludeTime = Boolean.FALSE;
                Boolean haveSearchParameter= Boolean.FALSE;
                for (int i = 0; i < parameters.size(); i++) {

                    VariableElement param = parameters.get(i);
                    if (parameters.get(i).getAnnotation(IncludeTime.class) != null) {
                        if (param.asType().toString().equals("java.util.Date") || param.asType().toString().equals("java.time.LocalDateTime")) {
                        } else {
                            message = nameOfMethod + "() @IncludeTime should only be applied to Date or DateTime fields.";
                            return Boolean.FALSE;
                        }
                        includeTimeFields.add(param.getSimpleName().toString());
                    } else {
                        if (parameters.get(i).getAnnotation(ExcludeTime.class) != null) {
                            if (param.asType().toString().equals("java.util.Date") || param.asType().toString().equals("java.time.LocalDateTime")) {
                            } else {
                                message = nameOfMethod + "() @ExcludeTime should only be applied to Date or DateTime fields.";
                                return Boolean.FALSE;
                            }
                            excludeTimeFields.add(param.getSimpleName().toString());
                        }
                    }

                    // Verifica que el tipo del parámetro sea un tipo aceptado
                    if (!JmoordbCoreUtil.isJmoordbJavaTypeValid(param.asType().toString())) {
                        message = nameOfMethod + "() parameter: " + param.getSimpleName().toString() + " Not a valid data type for a parameter.";
                        return Boolean.FALSE;
                    }
                    if (param.asType().toString().equals("java.lang.String")) {
                        haveStringParameters = Boolean.TRUE;
                        countStringParameters++;
                    }

                    if (param.asType().toString().equals(param.asType().toString().equals("com.jmoordb.core.model.Search[]"))) {
                        message = nameOfMethod + "() parameter: " + param.getSimpleName().toString() + " Not a valid data type for a parameter.";
                        return Boolean.FALSE;
                    }
                    if (param.asType().toString().equals("com.jmoordb.core.model.Pagination")) {

                        havePaginationParameters = Boolean.TRUE;
                        controlPaginationSorted++;
                        repositoryMethod.setNameOfParametersPagination(param.getSimpleName().toString());
                        message = nameOfMethod + "() parameter: " + param.getSimpleName().toString() + " Not a valid data type for a parameter.";
                        return Boolean.FALSE;
                    }
                    if (param.asType().toString().equals("com.jmoordb.core.model.Sorted")) {
                        haveSortedParameters = Boolean.TRUE;

                        controlPaginationSorted++;
                        repositoryMethod.setNameOfParametersSorted(param.getSimpleName().toString());
                        message = nameOfMethod + "() parameter: " + param.getSimpleName().toString() + " Not a valid data type for a parameter.";
                        return Boolean.FALSE;
                    }
                       if (param.asType().toString().equals("com.jmoordb.core.model.Search")) {
                       haveSearchParameter = Boolean.TRUE;
                    }
                }
                 if(!haveSearchParameter){
                       message = nameOfMethod + "() must have a parameter of type Search.java.";
                        return Boolean.FALSE;
                }

            }

            repositoryMethod.setIncludeTimeFields(includeTimeFields);
            repositoryMethod.setExcludeTimeFields(excludeTimeFields);
            if (!haveStringParameters) {
                message = nameOfMethod + "() Must have at least one String parameter";
                return Boolean.FALSE;
            }
            if (!haveStringParameters && havePaginationParameters) {
                message = nameOfMethod + "() Must have at least one String parameter";
                return Boolean.FALSE;
            }
            if (countStringParameters > 1) {
                message = nameOfMethod + "() Must have only one String parameter";
                return Boolean.FALSE;
            }

            SearchCountLikeBy searchCountLikeBy = executableElement.getAnnotation(SearchCountLikeBy.class);
//Almacena el where
            if (!nameOfMethod.startsWith("searchCountLikeBy")) {
                message = nameOfMethod + "() should start with searchCountLikeBy";
                return Boolean.FALSE;
            }
            String text = nameOfMethod;

            /**
             * Valida que si se usa Pagination y Sorted estos se incluyan en el
             * nombre
             */
            if (!validatePaginationSertedParameter(havePaginationParameters,
                    haveSortedParameters, text, nameOfMethod)) {
                return Boolean.FALSE;
            }
            /**
             * searchCountLikeBy
             */
            if (nameOfMethod.startsWith("searchCountLikeBy")) {
                text = text.replace("searchCountLikeBy", "").trim();
                text = NameOfMethodAnalizerUtil.simplifyText(text);

                if (!processFindBy(text, havePaginationParameters, haveSortedParameters, parameters, nameOfMethod)) {
                    return Boolean.FALSE;
                }
                worldAndToken = NameOfMethodAnalizerUtil.generarTokens(text);
         
//                lexemas = NameOfMethodAnalizerUtil.generarLexemas(worldAndToken);
                lexemas = NameOfMethodAnalizerUtil.generarLexemasSearchBy(worldAndToken);

                /*
                Valida las reglas
                 */
//                String result = NameOfMethodAnalizerUtil.validRules(lexemas, parameters.size());
                String result = NameOfMethodAnalizerUtil.validRulesSearchBy(lexemas, parameters.size());
                worldAndToken = NameOfMethodAnalizerUtil.joinFields(lexemas, parameters.size(), worldAndToken);
                lexemas = NameOfMethodAnalizerUtil.generarLexemasSearchBy(worldAndToken);
//                lexemas = NameOfMethodAnalizerUtil.generarLexemas(worldAndToken);
               
                if (result.equals("")) {
                    if ((NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "F") + NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "P") + NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "S")) > parameters.size()) {
                        message = nameOfMethod + "()x.0  The declared parameters do not match the method name definition.";
                        return Boolean.FALSE;
                    }
                    // cumple las reglas
                } else {
                    if (result.equals("x.1 The declared parameters do not match the method definition.")) {

                        worldAndToken = NameOfMethodAnalizerUtil.joinFields(lexemas, parameters.size(), worldAndToken);

//                        lexemas = NameOfMethodAnalizerUtil.generarLexemas(worldAndToken);
                        lexemas = NameOfMethodAnalizerUtil.generarLexemasSearchBy(worldAndToken);

//                        result = NameOfMethodAnalizerUtil.validRules(lexemas, parameters.size());
                        result = NameOfMethodAnalizerUtil.validRulesSearchBy(lexemas, parameters.size());
                        if (result.equals("")) {
                            if ((NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "F") + NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "P") + NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemas, "S")) != parameters.size()) {
                                message = nameOfMethod + "()x.2  The declared parameters do not match the method name definition.";
                                return Boolean.FALSE;
                            }
                            //cumple las reglas

                        } else {
                            message = nameOfMethod + "()x.3 rule infringed: " + result;
                            return Boolean.FALSE;

                        }

                    } else {
                        message = nameOfMethod + "() x.4 rule infringed: " + result;
                        return Boolean.FALSE;
                    }

                }

            }

            /**
             * Corrige agregando Comparator Equals a los campos que no lo tienen
             * esto ayuda al generador de codigo
             */
            /**
             * Wordl and Lexemas locales
             */
            List<String> worldTokenLocal = new ArrayList<>();
            List<String> lexemasLocal = new ArrayList<>();

            for (int i = 0; i < worldAndToken.size(); i++) {
                Integer next = i + 1;
                if (next == worldAndToken.size()) {
                    next = i;
                }

                worldTokenLocal.add(worldAndToken.get(i));

                lexemasLocal.add(lexemas.get(i));
                if (i != worldAndToken.size()) {
                    if (lexemas.get(i).equals("F")) {
                        if (!lexemas.get(next).equals("C")) {

                            worldTokenLocal.add("Equals");
                            lexemasLocal.add("C");
                        }
                    }
                }

            }

            if (!NameOfMethodAnalizerUtil.validLexemaComparatorOrder(lexemasLocal)) {
                message = nameOfMethod + "() the method name is not correct.";
                return Boolean.FALSE;
            }

            if (NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemasLocal, "F") > 1
                    || NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemasLocal, "C") > 1
                    || NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemasLocal, "L") > 0
                    || NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemasLocal, "P") > 0
                    || NameOfMethodAnalizerUtil.countOfTypeOfLexema(lexemasLocal, "S") > 0) {

                message = nameOfMethod + "() the method name is not correct. 2222";
                return Boolean.FALSE;
            }

            /**
             * Actualiza atributos de RepositoryMethod
             */
            repositoryMethod.setCaseSensitive(searchCountLikeBy.caseSensitive());
            repositoryMethod.setLikeByType(searchCountLikeBy.likeByType());
            repositoryMethod.setLexemas(lexemasLocal);
            repositoryMethod.setWorldAndToken(worldTokenLocal);
            repositoryMethod.setHavePagination(havePaginationParameters);
            repositoryMethod.setHaveSorted(haveSortedParameters);
            repositoryMethod.setWhereDescomposed(ProcessorUtil.generateWhereDescomposed(repositoryMethod));

            return Boolean.TRUE;
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="validatePaginationSertedParameter(Boolean havePaginationParameters, Boolean haveSortedParameters, String text, String nameOfMethod)">
    private static boolean validatePaginationSertedParameter(Boolean havePaginationParameters, Boolean haveSortedParameters, String text, String nameOfMethod) {
        try {
            if (havePaginationParameters && !text.contains("Pagination")) {
                message = nameOfMethod + "() If you define a parameter of type Pagination add to the name Pagination";
                return Boolean.FALSE;
            }
            if (haveSortedParameters && !text.contains("Sorted")) {
                message = nameOfMethod + "() If you define a parameter of type Sorted add to the name Sorted";
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean processFindAll(String text, Boolean havePaginationParameters, Boolean haveSortedParameters, List<? extends VariableElement> parameters,String nameOfMethod) )">
    private static Boolean processFindAll(String text, Boolean havePaginationParameters, Boolean haveSortedParameters, List<? extends VariableElement> parameters, String nameOfMethod) {
        try {

            /**
             * Verifica si es un findAll()
             */
            if (text.equals("")) {
                if (parameters.size() == 0) {
                    //es un findAll sin parametros
                } else {
                    message = nameOfMethod + "() Has parameters and they have not been defined in the method name";
                    return Boolean.FALSE;
                }
                //es findAll()   

                //if(parametros == 0) { es un fimdAll()}
            } else {
                /**
                 * Tiene más elementos en el nombre
                 */

                if (parameters.size() > 0) {
                    if (parameters.size() == 1) {
                        if (havePaginationParameters || haveSortedParameters) {
                        } else {
                            message = nameOfMethod + "() Must have parameter of type Pagination or Sorted";
                            return Boolean.FALSE;
                        }
                    } else {
                        if (parameters.size() == 2) {
                            if (havePaginationParameters && haveSortedParameters) {
                            } else {
                                message = nameOfMethod + "() Must have parameter of type Pagination and Sorted\".";
                                return Boolean.FALSE;
                            }
                        } else {
                            if (parameters.size() > 2) {
                                message = nameOfMethod + "() Only a maximum of two parameters are allowed. (Pagination/Sorted)";
                                return Boolean.FALSE;
                            }
                        }
                    }

                } else {
                    message = nameOfMethod + "() No declared parameters";
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean processFindBy(String text, Boolean havePaginationParameters, Boolean haveSortedParameters, List<? extends VariableElement> parameters, String nameOfMethod) ">

    private static Boolean processFindBy(String text, Boolean havePaginationParameters, Boolean haveSortedParameters, List<? extends VariableElement> parameters, String nameOfMethod) {
        try {
            if (text.equals("")) {
                message = nameOfMethod + "() You must add names in the method name";
                return Boolean.FALSE;
            }

            if (parameters.size() == 0) {

                message = nameOfMethod + "() It has no defined parameters";
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error() " + e.getLocalizedMessage());
        }
        return Boolean.FALSE;
    }
// </editor-fold>

}
