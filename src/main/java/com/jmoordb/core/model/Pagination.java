/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmoordb.core.model;

import com.jmoordb.core.util.MessagesUtil;
import static com.jmoordb.core.util.MessagesUtil.nameOfClass;
import static com.jmoordb.core.util.MessagesUtil.nameOfMethod;

/**
 *
 * @author avbravo
 */
public class Pagination {

    private Integer page;
    private Integer size;


    public Pagination() {
    }

    public Pagination(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer next() {
        try {
            if (page < size) {
                page++;
            }
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error " + e.getLocalizedMessage());
        }
        return page;
    }

    // <editor-fold defaultstate="collapsed" desc="Integer skip()">
    public Integer skip() {
        Integer result = 0;
        try {
            result = page > 0 ? ((page - 1) * size) : 0;
        } catch (Exception e) {
            MessagesUtil.error(MessagesUtil.nameOfClassAndMethod() + " error " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="limit()">

    public Integer limit() {

        return size;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static class Builder">
    public static class Builder {

        private Integer page;
        private Integer size;

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public Pagination build() {
            return new Pagination(
                    page,
                    size
            );
        }

    }
    // </editor-fold>
    
    
     public static class Move {
          // <editor-fold defaultstate="collapsed" desc="Integer numberOfPages(Integer rows,Integer rowForPage)">
   static Integer numberOfPages(Integer rows, Integer rowForPage) {
        Integer numberOfPage = 1;
        try {

            if (rows > 0) {
                numberOfPage = rows / rowForPage;
                if ((rows % rowForPage) > 0) {
                    numberOfPage++;
                }
            }
         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return numberOfPage;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Long numberOfPages(Long rows,Long rowForPage)">
   static Long numberOfPages(Long rows,Long rowForPage) {
     Long numberOfPage = 1L;
        try {

            if (rows > 0) {
                numberOfPage = rows / rowForPage;
                if ((rows % rowForPage) > 0) {
                    numberOfPage++;
                }
            }
         } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + nameOfClass() + " Metodo:" + nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
          
        }
        return numberOfPage;
    }
    // </editor-fold>

         
     }
    
   
}
