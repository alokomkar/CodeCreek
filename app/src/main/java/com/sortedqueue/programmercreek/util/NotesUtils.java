package com.sortedqueue.programmercreek.util;

import com.sortedqueue.programmercreek.database.NotesModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alok Omkar on 2017-07-28.
 */

public class NotesUtils {

    public static final String ALL_KEYWORDS = "break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,alignof,align_union,asm,axiom,bool,concept,concept_map,const_cast,constexpr,decltype,delegate,dynamic_cast,explicit,export,friend,generic,late_check,mutable,namespace,nullptr,property,reinterpret_cast,static_assert,static_cast,template,typeid,typename,using,virtual,where,break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,abstract,assert,boolean,byte,extends,final,finally,implements,import,instanceof,interface,null,native,package,strictfp,super,synchronized,throws,transient,as,base,by,checked,decimal,delegate,descending,dynamic,event,fixed,foreach,from,group,implicit,in,internal,into,is,let,lock,object,out,override,orderby,params,partial,readonly,ref,sbyte,sealed,stackalloc,string,select,uint,ulong,unchecked,unsafe,ushort,var,virtual,where,break,continue,do,else,for,if,return,while,auto,case,char,const,default,double,enum,extern,float,goto,inline,int,long,register,short,signed,sizeof,static,struct,switch,typedef,union,unsigned,void,volatile,catch,class,delete,false,import,new,operator,private,protected,public,this,throw,true,try,typeof,debugger,eval,export,function,get,null,set,undefined,var,with,Infinity,NaN,caller,delete,die,do,dump,elsif,eval,exit,foreach,for,goto,if,import,last,local,my,next,no,our,print,package,redo,require,sub,undef,unless,until,use,wantarray,while,BEGIN,END,break,continue,do,else,for,if,return,while,and,as,assert,class,def,del,elif,except,exec,finally,from,global,import,in,is,lambda,nonlocal,not,or,pass,print,raise,try,with,yield,False,True,None,break,continue,do,else,for,if,return,while,alias,and,begin,case,class,def,defined,elsif,end,ensure,false,in,module,next,nil,not,or,redo,rescue,retry,self,super,then,true,undef,unless,until,when,yield,BEGIN,END,break,continue,do,else,for,if,return,while,case,done,elif,esac,eval,fi,function,in,local,set,then,until";

    public static String[] keywords = ALL_KEYWORDS.split(",");
    public static List<String> stringList = new ArrayList<String>(Arrays.asList(keywords));

    public static boolean isCode( String noteLine ) {

        if( noteLine.startsWith("#") || noteLine.endsWith(";") || noteLine.contains("{") || noteLine.contains("}")) {
            return true;
        }
        String[] words = noteLine.replaceAll(";", "").split(" ");
        int count = 0;
        for( String word : words ) {
            if( stringList.contains(word) ) count++;
        }
        if( count > 1 ) {
            return true;
        }
        return false;
    }

    public static ArrayList<NotesModel> splitParaIntoNotes(String programCode) {
        String lines[] = programCode.split("\\r?\\n");
        ArrayList<NotesModel> notesModelArrayList = new ArrayList<>();
        int index = 0;
        for( String line : lines ) {
            if( !line.trim().isEmpty() ) {

                boolean isCode = isCode(line);
                if( index == 0 && !isCode ) notesModelArrayList.add(new NotesModel(line, NotesModel.TYPE_HEADER));
                else if( isCode ) notesModelArrayList.add(new NotesModel(line, NotesModel.TYPE_CODE));
                else notesModelArrayList.add(new NotesModel(line, NotesModel.TYPE_NOTES));



                index++;
            }
        }
        return notesModelArrayList;
    }

}
